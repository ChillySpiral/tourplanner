package fhtw.at.tourplanner.listener;

import fhtw.at.tourplanner.DAL.model.TourModel;

import java.util.List;

public interface TourSearchListener {
    void displaySearchResults(List<TourModel> tourIds);
}
