package fhtw.at.tourplanner.view;

import fhtw.at.tourplanner.viewmodel.TourEditViewModel;
import fhtw.at.tourplanner.viewmodel.TourTabViewModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TourTabController {
    @FXML
    public TextField tourTitle;

    @FXML
    private Button editButton;

    @FXML
    private TextArea descriptionText;

    @FXML
    public TextField detailsFrom;

    @FXML
    public TextField detailsTo;

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
        descriptionText.textProperty().bindBidirectional(tourTabViewModel.descriptionProperty());
        detailsFrom.textProperty().bindBidirectional(tourTabViewModel.detailsFromProperty());
        detailsTo.textProperty().bindBidirectional(tourTabViewModel.detailsToProperty());
    }

    public void editTour(ActionEvent actionEvent) {

        //ToDo: Implement Text fields Description, From, To, etc. and add them here
        //ToDo: Implement ComboBox with Enum for TransportType !Warning: This has to be addressed separately
        var result = new TourEditViewModel(tourTitle.getText(), descriptionText.getText(), detailsFrom.getText(), detailsTo.getText());
        var dialog = new EditDialog(tourTitle.getScene().getWindow(), result);

        dialog.showAndWait().ifPresent(x -> {

                tourTitle.setText(result.getTitle());
                descriptionText.setText((result.getDescription()));
        });
    }
}
