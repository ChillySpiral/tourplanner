package fhtw.at.tourplanner.DAL;

import fhtw.at.tourplanner.model.TourModel;

import java.util.List;

public interface TourDataLogic {
    List<TourModel> GetAllTours();
}
