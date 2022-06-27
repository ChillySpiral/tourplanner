package fhtw.at.tourplanner.view;

import fhtw.at.tourplanner.DAL.model.enums.TransportType;
import fhtw.at.tourplanner.viewmodel.TourEditViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TourEditController {

    private final TourEditViewModel tourEditViewModel;
    @FXML
    private TextField title;

    @FXML
    private TextArea description;

    @FXML
    private TextField from;

    @FXML
    private TextField to;

    @FXML
    private ComboBox<TransportType> transportType;

    @FXML
    private ButtonType ok_button;

    @FXML
    public void initialize() {
        title.textProperty().bindBidirectional(tourEditViewModel.titleProperty());
        description.textProperty().bindBidirectional(tourEditViewModel.descriptionProperty());
        from.textProperty().bindBidirectional(tourEditViewModel.fromProperty());
        to.textProperty().bindBidirectional(tourEditViewModel.toProperty());
        transportType.getItems().addAll(TransportType.Bicycle, TransportType.Car, TransportType.Foot);
        transportType.valueProperty().bindBidirectional(tourEditViewModel.transportTypeProperty());
    }

    public TourEditController(TourEditViewModel tourEditViewModel){

        this.tourEditViewModel = tourEditViewModel;
    }
}
