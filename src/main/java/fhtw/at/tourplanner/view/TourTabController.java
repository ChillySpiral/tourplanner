package fhtw.at.tourplanner.view;

import fhtw.at.tourplanner.viewmodel.TourTabViewModel;
import javafx.fxml.FXML;

public class TourTabController {
    private final TourTabViewModel tourTabViewModel;

    public TourTabController(TourTabViewModel tourTabViewModel) {
        this.tourTabViewModel = tourTabViewModel;
    }

    @FXML
    public void initialize() {

    }
}
