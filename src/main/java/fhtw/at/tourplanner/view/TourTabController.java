package fhtw.at.tourplanner.view;

import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.enums.TransportType;
import fhtw.at.tourplanner.viewmodel.TourEditViewModel;
import fhtw.at.tourplanner.viewmodel.TourTabViewModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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

    @FXML
    public ImageView image;

    @FXML
    public ComboBox<TransportType> detailsTransportType;

    private final TourTabViewModel tourTabViewModel;

    public TourTabController(TourTabViewModel tourTabViewModel) {
        this.tourTabViewModel = tourTabViewModel;
    }

    //ToDo: Marker: Fields are all going to be readonly, and should not affect the data in the Viewmodel
    @FXML
    public void initialize() {
        tourTitle.disableProperty().bind(Bindings.isNull(tourTabViewModel.titleProperty()));
        tourTitle.textProperty().bind(tourTabViewModel.titleProperty());
        editButton.disableProperty().bind(Bindings.isNull(tourTabViewModel.titleProperty()));
        //ToDo: Alle Properties müssen hier gebindet werden (nicht umbedingt immer BiDirectional)
        descriptionText.textProperty().bind(tourTabViewModel.descriptionProperty());
        detailsFrom.textProperty().bind(tourTabViewModel.detailsFromProperty());
        detailsTo.textProperty().bind(tourTabViewModel.detailsToProperty());
        image.imageProperty().bind(tourTabViewModel.imageProperty());
        detailsTransportType.getItems().addAll(TransportType.Bicycle, TransportType.Car, TransportType.Foot);
        detailsTransportType.valueProperty().bind(tourTabViewModel.transportTypeProperty());
    }

    //ToDo: Marker: We have access to the ViewModel that Updates the data and sends it to the DB
    public void editTour(ActionEvent actionEvent) {

        //ToDo: Implement Text fields Description, From, To, etc. and add them here
        //ToDo: Implement ComboBox with Enum for TransportType !Warning: This has to be addressed separately
        var result = new TourEditViewModel(tourTitle.getText(), descriptionText.getText(), detailsFrom.getText(), detailsTo.getText(), detailsTransportType.getValue());
        var dialog = new EditDialog(tourTitle.getScene().getWindow(), result);

        dialog.showAndWait().ifPresent(x -> {
                TourModel tourModel = new TourModel();
                tourModel.setTitle(result.getTitle());
                tourModel.setDescription((result.getDescription()));
                tourModel.setFrom(result.getFrom());
                tourModel.setTo(result.getTo());
                tourModel.setTransportType(result.getTransportType());

                tourTabViewModel.updateTourModel(tourModel);
        });
    }
}
