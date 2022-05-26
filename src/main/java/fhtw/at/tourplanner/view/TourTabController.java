package fhtw.at.tourplanner.view;

import fhtw.at.tourplanner.viewmodel.TourTabViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class TourTabController {
    @FXML
    public TextField tourTitle;

    private final TourTabViewModel tourTabViewModel;

    public TourTabController(TourTabViewModel tourTabViewModel) {
        this.tourTabViewModel = tourTabViewModel;
    }

    @FXML
    public void initialize() {
        tourTitle.textProperty().bindBidirectional(tourTabViewModel.titleProperty());
        //ToDo: Alle Properties müssen hier gebindet werden (nicht umbedingt immer BiDirectional)
    }
}
