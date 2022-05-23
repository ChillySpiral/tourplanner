package fhtw.at.tourplanner.view;

import fhtw.at.tourplanner.model.TourModel;
import fhtw.at.tourplanner.viewmodel.TourListViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class TourListController {
    @FXML
    public ListView<TourModel> tourModelListView;

    private final TourListViewModel tourListViewModel;

    public TourListController(TourListViewModel tourListViewModel) {
        this.tourListViewModel = tourListViewModel;
    }

    @FXML
    public void initialize() {
        tourModelListView.setItems(tourListViewModel.getData());
        tourModelListView.getSelectionModel().selectedItemProperty().addListener(tourListViewModel.getChangeListener());
    }

    public void addNewTour(ActionEvent actionEvent) {
        tourListViewModel.addNewTour();
    }

    public void deleteTour(ActionEvent actionEvent){
        tourListViewModel.deleteTour(tourModelListView.getSelectionModel().getSelectedItem());
    }
}
