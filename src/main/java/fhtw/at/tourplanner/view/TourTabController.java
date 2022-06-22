package fhtw.at.tourplanner.view;

import fhtw.at.tourplanner.viewmodel.TourEditViewModel;
import fhtw.at.tourplanner.viewmodel.TourTabViewModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TourTabController {
    @FXML
    public TextField tourTitle;

    @FXML
    private Button editButton;

    private final TourTabViewModel tourTabViewModel;

    public TourTabController(TourTabViewModel tourTabViewModel) {
        this.tourTabViewModel = tourTabViewModel;
    }

    @FXML
    public void initialize() {
        tourTitle.disableProperty().bind(Bindings.isNull(tourTabViewModel.titleProperty()));
        tourTitle.textProperty().bindBidirectional(tourTabViewModel.titleProperty());
        editButton.disableProperty().bind(Bindings.isNull(tourTabViewModel.titleProperty()));
        //ToDo: Alle Properties müssen hier gebindet werden (nicht umbedingt immer BiDirectional)
    }

    public void editTour(ActionEvent actionEvent) {

        //ToDo: Implement Text fields Description, From, To, etc. and add them here
        //ToDo: Implement ComboBox with Enum for TransportType !Warning: This has to be addressed separately
        var result = new TourEditViewModel(tourTitle.getText(), "Description Text", "From Text", "To Text");
        var dialog = new EditDialog(tourTitle.getScene().getWindow(), result);

        dialog.showAndWait().ifPresent(x -> tourTitle.setText(result.getTitle()));
    }
}
