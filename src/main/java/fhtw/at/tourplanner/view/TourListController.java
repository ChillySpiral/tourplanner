package fhtw.at.tourplanner.view;

import fhtw.at.tourplanner.viewmodel.TourListViewModel;
import javafx.fxml.FXML;

public class TourListController {
    private final TourListViewModel tourListViewModel;

    public TourListController(TourListViewModel tourListViewModel) {
        this.tourListViewModel = tourListViewModel;
    }

    @FXML
    public void initialize() {

    }
}
