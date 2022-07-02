package fhtw.at.tourplanner.view;

import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.enums.Difficulty;
import fhtw.at.tourplanner.DAL.model.enums.Rating;
import fhtw.at.tourplanner.DAL.model.enums.TransportType;
import fhtw.at.tourplanner.view.dialog.LogEditDialog;
import fhtw.at.tourplanner.view.dialog.TourEditDialog;
import fhtw.at.tourplanner.viewmodel.TourTabViewModel;
import fhtw.at.tourplanner.viewmodel.dialog.LogEditViewModel;
import fhtw.at.tourplanner.viewmodel.dialog.TourEditViewModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

    @FXML
    public TextField detailsEstimatedTime;

    @FXML
    public TextField detailsDistance;

    @FXML
    private TableView<TourLog> logTableView;

    @FXML
    private TableColumn<TourLog, LocalDateTime> logDate;

    @FXML
    private TableColumn<TourLog, LocalTime> logDuration;

    @FXML
    private TableColumn<TourLog, Difficulty> logDifficulty;

    @FXML
    private TableColumn<TourLog, String> logComment;

    @FXML
    private TableColumn<TourLog, Rating> logRating;

    @FXML
    private Button editLogButton;

    @FXML
    private AnchorPane imageAnchorPane;

    private final TourTabViewModel tourTabViewModel;

    public TourTabController(TourTabViewModel tourTabViewModel) {
        this.tourTabViewModel = tourTabViewModel;
    }

    @FXML
    public void initialize() {
        tourTitle.disableProperty().bind(Bindings.isNull(tourTabViewModel.titleProperty()));
        tourTitle.textProperty().bind(tourTabViewModel.titleProperty());
        editButton.disableProperty().bind(Bindings.isNull(tourTabViewModel.titleProperty()));
        descriptionText.textProperty().bind(tourTabViewModel.descriptionProperty());
        detailsFrom.textProperty().bind(tourTabViewModel.detailsFromProperty());
        detailsTo.textProperty().bind(tourTabViewModel.detailsToProperty());
        image.imageProperty().bind(tourTabViewModel.imageProperty());
        detailsTransportType.getItems().addAll(TransportType.Bicycle, TransportType.Car, TransportType.Foot);
        detailsTransportType.valueProperty().bind(tourTabViewModel.transportTypeProperty());
        detailsEstimatedTime.textProperty().bind(tourTabViewModel.estimatedTimeProperty());
        detailsDistance.textProperty().bind(tourTabViewModel.distanceProperty());

        logDate.setCellValueFactory(new PropertyValueFactory<TourLog, LocalDateTime>("dateTime"));
        logDuration.setCellValueFactory(new PropertyValueFactory<TourLog, LocalTime>("totalTime"));
        logDifficulty.setCellValueFactory(new PropertyValueFactory<TourLog, Difficulty>("difficulty"));
        logComment.setCellValueFactory(new PropertyValueFactory<TourLog, String>("comment"));
        logRating.setCellValueFactory(new PropertyValueFactory<TourLog, Rating>("rating"));
        logTableView.setItems(tourTabViewModel.getLogData());

        image.fitHeightProperty().bind(imageAnchorPane.heightProperty());
        image.fitWidthProperty().bind(imageAnchorPane.widthProperty());
    }

    public void editTour(ActionEvent actionEvent) {
        var result = new TourEditViewModel(tourTitle.getText(), descriptionText.getText(), detailsFrom.getText(), detailsTo.getText(), detailsTransportType.getValue());
        var dialog = new TourEditDialog(tourTitle.getScene().getWindow(), result);

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

    public void addNewLog(ActionEvent actionEvent) {
        TourLog log = tourTabViewModel.addNewLog();
        var result = new LogEditViewModel(null, log.getComment(), log.getDifficulty(), log.getRating());
        var dialog = new LogEditDialog(tourTitle.getScene().getWindow(), result);
        final boolean[] set = {false};

        dialog.showAndWait().ifPresent(x -> {
            TourLog tourLog = new TourLog();

            tourLog.setLogId(log.getLogId());
            tourLog.setComment((result.getComment()));
            tourLog.setDifficulty(result.getDifficulty());
            tourLog.setRating(result.getRating());
            if(!result.getDuration().isEmpty())
                tourLog.setTotalTime(LocalTime.parse(result.getDuration(), DateTimeFormatter.ISO_LOCAL_TIME));
            else
                tourLog.setTotalTime(LocalTime.of(0,0,0));

            tourTabViewModel.editTourLogData(tourLog);
            set[0] =true;
        });

        if(!set[0]) {
            tourTabViewModel.deleteLog(log);
        }
        else {
            tourTabViewModel.updateTourLogData();
        }

    }

    public void deleteLog(ActionEvent actionEvent){
        TourLog log = logTableView.getSelectionModel().getSelectedItem();
        if(log == null)
            return;
        tourTabViewModel.deleteLog(log);
    }

    public void editLog(ActionEvent actionEvent) {
        TourLog log = logTableView.getSelectionModel().getSelectedItem();
        if(log == null)
            return;
        var result = new LogEditViewModel(log.getTotalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")), log.getComment(), log.getDifficulty(), log.getRating());
        var dialog = new LogEditDialog(tourTitle.getScene().getWindow(), result);

        dialog.showAndWait().ifPresent(x -> {
            TourLog tourLog = new TourLog();

            tourLog.setLogId(log.getLogId());
            tourLog.setComment((result.getComment()));
            tourLog.setDifficulty(result.getDifficulty());
            tourLog.setRating(result.getRating());
            if(!result.getDuration().isEmpty())
                tourLog.setTotalTime(LocalTime.parse(result.getDuration(), DateTimeFormatter.ISO_LOCAL_TIME));
            else
                tourLog.setTotalTime(log.getTotalTime());

            tourTabViewModel.editTourLogData(tourLog);
            tourTabViewModel.updateTourLogData();
        });
    }

}
