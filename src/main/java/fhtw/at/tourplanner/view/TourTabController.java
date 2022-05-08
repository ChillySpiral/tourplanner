package fhtw.at.tourplanner.view;

import fhtw.at.tourplanner.listener.BindListener;
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
        tourTitle.textProperty().bindBidirectional(tourTabViewModel.data.getTitle());
        tourTabViewModel.addListener(new BindListener() {
            @Override
            public void requestReBind() {
                tourTitle.textProperty().bindBidirectional(tourTabViewModel.data.getTitle());
            }
            @Override
            public void requestUnBind(){
                tourTitle.textProperty().unbindBidirectional(tourTabViewModel.data.getTitle());
            }
        });
    }
}
