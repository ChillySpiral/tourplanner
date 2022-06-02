package fhtw.at.tourplanner.DAL.dao.implementation;

import fhtw.at.tourplanner.DAL.dao.DalFactory;
import fhtw.at.tourplanner.DAL.database.Database;
import fhtw.at.tourplanner.DAL.dao.extended.TourDaoExtension;
import fhtw.at.tourplanner.DAL.database.converter.ModelConverter;
import fhtw.at.tourplanner.model.TourLog;
import fhtw.at.tourplanner.model.TourModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TourDao implements TourDaoExtension {

    private final Database database;

    public TourDao() {
        this.database = DalFactory.GetDatabase();
    }

    @Override
    public Optional<TourModel> get(int id) {
        var queryString = "SELECT * FROM public.\"tour\" WHERE Id = CAST(? AS INTEGER);";

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
    public TourModel create() {
        var queryString = "INSERT INTO public.\"tour\" (\"Title\") VALUES(?);";
        var newItem = new TourModel();
        newItem.setTitle("New Tour");
        var newId = database.insert(queryString, getParameters(newItem));

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
