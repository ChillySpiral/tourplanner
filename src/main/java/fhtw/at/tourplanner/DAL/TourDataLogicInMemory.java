package fhtw.at.tourplanner.DAL;

import fhtw.at.tourplanner.model.TourModel;

import java.util.ArrayList;
import java.util.List;

public class TourDataLogicInMemory implements TourDataLogic{

    List<TourModel> allTours = new ArrayList<>();

    @Override
    public List<TourModel> GetAllTours() {

        var tour1 = new TourModel();
        tour1.setTitle("Donauinsel Walk");

        var tour2 = new TourModel();
        tour2.setTitle("WienerBerg Walk");

        allTours.add(tour1);
        allTours.add(tour2);
        return allTours;
    }
}
