package fhtw.at.tourplanner.BL;

import fhtw.at.tourplanner.DAL.model.TourModel;

import java.util.List;

public interface TourAppManager {
    List<TourModel> getAllTours();
    TourModel createTour();
    void deleteTour(TourModel tourModel);
    void updateTour(TourModel tourModel);
}
