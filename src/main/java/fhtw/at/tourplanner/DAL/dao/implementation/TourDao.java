package fhtw.at.tourplanner.DAL.dao.implementation;

import fhtw.at.tourplanner.DAL.FileSystem.FileSystem;
import fhtw.at.tourplanner.DAL.dao.extended.TourDaoExtension;
import fhtw.at.tourplanner.DAL.database.Database;
import fhtw.at.tourplanner.DAL.database.converter.ModelConverter;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.enums.TransportType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TourDao implements TourDaoExtension {

    private final Database database;
    private final FileSystem fileSystem;

    public TourDao(Database database, FileSystem fileSystem) {
        this.database = database;
        this.fileSystem = fileSystem;
    }

    @Override
    public Optional<TourModel> get(int id) {
        var queryString = "SELECT * FROM public.\"tour\" WHERE \"Id\" = CAST(? AS INTEGER);";

        List<Object> params = new ArrayList<>();
        params.add(id);

        var queryResult = database.select(queryString, params);
        if (queryResult.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ModelConverter.convertToTourModel(queryResult.get(0)));
    }

    @Override
    public List<TourModel> getAll() {
        var queryString = "SELECT * FROM public.\"tour\";";
        var queryResult = database.select(queryString, null);
        List<TourModel> result = new ArrayList<>();
        for (var tour : queryResult) {
            result.add(ModelConverter.convertToTourModel(tour));
        }
        return result;
    }

    @Override
    public TourModel create(int optId) {
        var queryString = "INSERT INTO public.\"tour\" (\"Title\", \"TransportType\") VALUES(?, ?);";
        var newItem = new TourModel();
        newItem.setTitle("New Tour");
        newItem.setTo("");
        newItem.setFrom("");
        newItem.setDescription("");
        newItem.setTransportType(TransportType.Foot);
        newItem.setEstimatedTime(LocalTime.of(0,0,0));
        newItem.setTourDistance(0.0);

        List<Object> paramsId = new ArrayList<>();
        paramsId.add(newItem.getTitle());
        paramsId.add(newItem.getTransportType().toString());
        var newId = database.insert(queryString, paramsId);

        if (newId == -1) {
            return null;
        }
        newItem.setTourId(newId);
        return newItem;
    }

    @Override
    public void update(TourModel tourModel) {
        var queryString = "UPDATE public.\"tour\" SET \"Title\" = ?, \"Description\" = ?, \"From\" = ?, \"To\" = ?, \"TransportType\" = ?, \"Distance\" = ?, \"EstimatedTime\" = ?, \"ImageFilename\" = ? WHERE \"Id\" = CAST(? AS INTEGER);";
        database.update(queryString, getParameters(tourModel));
    }

    @Override
    public void delete(TourModel tourModel) {
        var queryString = "DELETE FROM public.\"tour\" WHERE \"Id\" = CAST(? AS INTEGER);";
        List<Object> paramsId = new ArrayList<>();
        paramsId.add(tourModel.getTourId());
        fileSystem.deleteFile(tourModel);
        database.delete(queryString, paramsId);
    }

    @Override
    public List<TourLog> getLogsForTour(TourModel tour) {
        var queryString = "SELECT * FROM public.\"log\" WHERE \"TourId\" = CAST(? AS INTEGER);";
        List<Object> paramsId = new ArrayList<>();
        paramsId.add(tour.getTourId());

        var queryResult = database.select(queryString, paramsId);

        List<TourLog> result = new ArrayList<>();
        for (var log : queryResult) {
            result.add(ModelConverter.convertToTourLogModel(log));
        }
        return result;
    }

    private List<Object> getParameters(TourModel tour) {
        List<Object> params = new ArrayList<>();
        params.add(tour.getTitle());
        params.add(tour.getDescription());
        params.add(tour.getFrom());
        params.add(tour.getTo());
        params.add(tour.getTransportType());
        params.add(tour.getTourDistance());
        params.add(tour.getEstimatedTime());
        params.add(tour.getImageFilename());
        params.add(tour.getTourId());

        return params;
    }

}
