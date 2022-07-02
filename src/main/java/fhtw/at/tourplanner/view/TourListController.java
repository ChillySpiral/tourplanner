package fhtw.at.tourplanner.view;

import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.view.dialog.TourEditDialog;
import fhtw.at.tourplanner.viewmodel.TourListViewModel;
import fhtw.at.tourplanner.viewmodel.dialog.TourEditViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.Window;

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
        var newTour = tourListViewModel.addNewTour();
        var result = new TourEditViewModel(newTour.getTitle(), newTour.getDescription(), newTour.getFrom(), newTour.getTo(), newTour.getTransportType());
        Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().get();
        var dialog = new TourEditDialog(owner, result);
        final boolean[] set = {false};

        dialog.showAndWait().ifPresent(x -> {
            newTour.setTitle(result.getTitle());
            newTour.setDescription((result.getDescription()));
            newTour.setFrom(result.getFrom());
            newTour.setTo(result.getTo());
            newTour.setTransportType(result.getTransportType());

            tourListViewModel.updateTour(newTour);
            set[0] =true;
        });

        if(!set[0]) {
            tourListViewModel.deleteTour(newTour);
        }
    }

    public void deleteTour(ActionEvent actionEvent){
        tourListViewModel.deleteTour(tourModelListView.getSelectionModel().getSelectedItem());
    }
}
