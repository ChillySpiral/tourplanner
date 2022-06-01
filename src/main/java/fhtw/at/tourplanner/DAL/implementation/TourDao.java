package fhtw.at.tourplanner.DAL.implementation;

import fhtw.at.tourplanner.DAL.dao.DalFactory;
import fhtw.at.tourplanner.DAL.database.Database;
import fhtw.at.tourplanner.DAL.extended.TourDaoExtension;
import fhtw.at.tourplanner.model.TourLog;
import fhtw.at.tourplanner.model.TourModel;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class TourDao implements TourDaoExtension {

    private final Database database;

    public TourDao(){
        this.database = DalFactory.GetDatabase();
    }

    @Override
    public Optional<TourModel> get(int id) {
        var queryString = "SELECT * FROM public.\"tour\" WHERE Id = CAST(? AS INTEGER);";

        List<Object> params = new ArrayList();
        params.add(id);

        var queryResult = database.select(queryString, params);
        if(queryResult.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(convertToModel(queryResult.get(0)));
    }

    @Override
    public List<TourModel> getAll() {
        var queryString = "SELECT * FROM public.\"tour\";";
        var queryResult = database.select(queryString, null);
        List<TourModel> result = new ArrayList<>();
        for (var tour : queryResult){
            result.add(convertToModel(tour));
        }
        return result;
    }

    @Override
    public TourModel create() {
        var queryString = "INSERT INTO public.\"tour\" (\"Title\") VALUES(?);";
        var newItem = new TourModel();
        newItem.setTitle("New Tour");
        var newId = database.insert(queryString, getParameters(newItem));

        if(newId == -1){
            return null;
        }
        newItem.setTourId(newId);
        return newItem;
    }

    @Override
    public void update(TourModel tourModel) {

    }

    @Override
    public void delete(TourModel tourModel) {

    }

    @Override
    public List<TourLog> getLogsForTour(TourModel tour) {
        return null;
    }

    private TourModel convertToModel(HashMap<String, Object> params) {
        var id = Integer.parseInt(params.get("Id").toString());
        var title = (String) params.get("Title");
        var description = (String) params.get("Description");
        var from = (String) params.get("From");
        var to = (String) params.get("To");
        var transportType = (String) params.get("TransportType");
        var distance = params.get("Distance") == null ? 0 : Double.parseDouble(params.get("Distance").toString());
        LocalTime estimatedTime = params.get("EstimatedTime") == null ? LocalTime.of(0,0,0) :LocalTime.parse(params.get("EstimatedTime").toString());
        var imageFilename = (String) params.get("ImageFilename");


        return new TourModel(id, title, description, from, to, transportType, distance, estimatedTime, imageFilename);
    }

    private List<Object> getParameters(TourModel tour){
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
