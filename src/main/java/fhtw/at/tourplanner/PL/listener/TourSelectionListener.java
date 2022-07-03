package fhtw.at.tourplanner.PL.listener;

import fhtw.at.tourplanner.DAL.model.TourModel;

public interface TourSelectionListener {
    void changeSelection(TourModel tourItem);
}
