package fhtw.at.tourplanner.DAL.implementation;

import fhtw.at.tourplanner.DAL.extended.TourDaoExtension;
import fhtw.at.tourplanner.model.TourLog;
import fhtw.at.tourplanner.model.TourModel;
import fhtw.at.tourplanner.model.enums.RouteType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TourDaoInMemory implements TourDaoExtension {

    private List<TourModel> tours = new ArrayList<>();
    private List<TourLog> tourLogs = new ArrayList<>();

    public TourDaoInMemory(){
        tours.add(new TourModel(1,"Donauinsel Walk", "Not difficult", "Steinspornbrücke", "Floridsdorferbrücke", "RouteType.bicycle", 7.0, LocalTime.of(1, 20), ""));
        tours.add(new TourModel(2,"Wienerberg Walk", "Slightly difficult", "Wienerberg City", "Stefan Fadinger Platz", "RouteType.fastest", 2.0, LocalTime.of(0, 20), ""));
        tours.add(new TourModel(3,"Dachstein Walk", "Very difficult", "Dorf", "Spitze", "RouteType.pedestrian", 9.0, LocalTime.of(2, 20), ""));
    }


    @Override
    public Optional<TourModel> get(int id) {
        var tour = tours.stream().filter(x -> x.getTourId() == id).findFirst();
        return tour;
    }

    @Override
    public List<TourModel> getAll() {
        return tours;
    }

    @Override
    public TourModel create() {
        var newItem = new TourModel();
        newItem.setTitle("New Tour");
        tours.add(newItem);
        return newItem;
    }

    @Override
    public void update(TourModel tourModel) {
        if(tours.contains(tourModel)){
            var index = tours.indexOf(tourModel);
            var originalTour = tours.get(index);

            originalTour.setTitle(tourModel.getTitle());
            originalTour.setDescription(tourModel.getDescription());
            originalTour.setFrom(tourModel.getFrom());
            originalTour.setTo(tourModel.getTo());
            originalTour.setTourDistance(tourModel.getTourDistance());
            originalTour.setEstimatedTime(tourModel.getEstimatedTime());
            originalTour.setImageFilename(tourModel.getImageFilename());
            originalTour.setTransportType(tourModel.getTransportType());
        }
    }

    @Override
    public void delete(TourModel tourModel) {
        if(tours.contains(tourModel)){
            var index = tours.indexOf(tourModel);
            tours.remove(index);
        }
    }

    @Override
    public List<TourLog> getLogsForTour(TourModel tour) {
        return null;
    }
}
