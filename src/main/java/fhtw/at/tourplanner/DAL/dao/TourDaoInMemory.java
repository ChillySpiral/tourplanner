package fhtw.at.tourplanner.DAL.dao;

import fhtw.at.tourplanner.model.TourModel;
import lombok.Builder;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TourDaoInMemory implements Dao<TourModel> {

    private List<TourModel> tours = new ArrayList<>();

    public TourDaoInMemory(){
        tours.add(new TourModel(1,"Donauinsel Walk", "Not difficult", "Steinspornbrücke", "Floridsdorferbrücke", "BICYCLE", 7.0, LocalTime.of(1, 20), -1));
        tours.add(new TourModel(2,"Wienerberg Walk", "Slightly difficult", "Wienerberg City", "Stefan Fadinger Platz", "WALKING", 2.0, LocalTime.of(0, 20), -1));
        tours.add(new TourModel(3,"Dachstein Walk", "Very difficult", "Dorf", "Spitze", "WALKING", 9.0, LocalTime.of(2, 20), -1));
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
    public void update(TourModel tourModel, List<?> params) {

    }

    @Override
    public void delete(TourModel tourModel) {
        if(tours.contains(tourModel)){
            var index = tours.indexOf(tourModel);
            tours.remove(index);
        }
    }
}
