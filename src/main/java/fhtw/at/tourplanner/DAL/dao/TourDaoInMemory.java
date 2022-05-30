package fhtw.at.tourplanner.DAL.dao;

import fhtw.at.tourplanner.model.TourModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TourDaoInMemory implements Dao<TourModel> {

    private final List<TourModel> tours = new ArrayList<>();

    public TourDaoInMemory(){
        tours.add(new TourModel("Donauinsel Walk", "Not difficult"));
        tours.add(new TourModel("Wienerberg Walk", "Slightly difficult"));
        tours.add(new TourModel("Dachstein Walk", "Very difficult"));
        tours.add(new TourModel("Gletscher Climb", "Very difficult"));
        tours.add(new TourModel("Prater Walk", "Not difficult"));
    }


    @Override
    public Optional<TourModel> get(int id) {
        return Optional.empty();
    }

    @Override
    public List<TourModel> getAll() {
        return tours;
    }

    @Override
    public TourModel create() {
        return null;
    }

    @Override
    public void update(TourModel tourModel, List<?> params) {

    }

    @Override
    public void delete(TourModel tourModel) {

    }
}
