package fhtw.at.tourplanner.PL.viewmodel;

import fhtw.at.tourplanner.BL.appManager.TourAppManager;
import fhtw.at.tourplanner.BL.calculator.implementation.CalculatorImpl;
import fhtw.at.tourplanner.Configuration.AppConfigurationLoader;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.enums.Difficulty;
import fhtw.at.tourplanner.DAL.model.enums.TransportType;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class TourTabViewModel {

    @Getter
    private TourModel data;
    private volatile boolean isInitialValue = false;
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty detailsFrom = new SimpleStringProperty();
    private final StringProperty detailsTo = new SimpleStringProperty();
    private final ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<TransportType> transportType = new SimpleObjectProperty<>();
    private final StringProperty estimatedTime = new SimpleStringProperty();
    private final StringProperty distance = new SimpleStringProperty();
    private final TourAppManager tourAppManager;
    private final ObservableList<TourLog> logData = FXCollections.observableArrayList();

    private final StringProperty popularity = new SimpleStringProperty();
    private final StringProperty childfriendliness = new SimpleStringProperty();
    private final TourUpdateService tourUpdateService;


    public TourTabViewModel(TourAppManager tourAppManager) {
        this.tourAppManager = tourAppManager;
        this.tourUpdateService = new TourUpdateService();
        registerListenerForTourUpdateService();
    }

    public String getTitle() {
        return title.get();
    }

    public String getDescription() {
        return description.get();
    }

    public String getDetailsFrom() {
        return detailsFrom.get();
    }

    public String getDetailsTo() { return detailsTo.get(); }

    public TransportType getTransportType() {
        return transportType.getValue();
    }
    public String getEstimatedTime(){
        return estimatedTime.get();
    }
    public String getDistance(){
        return distance.get();
    }
    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty detailsFromProperty() {
        return detailsFrom;
    }

    public StringProperty detailsToProperty() {
        return detailsTo;
    }

    public ObjectProperty<Image> imageProperty() {
        return imageProperty;
    }

    public StringProperty popularityProperty() {
        return popularity;
    }
    public StringProperty childfriendlinessProperty() {
        return childfriendliness;
    }

    public ObjectProperty<TransportType> transportTypeProperty() {
        return transportType;
    }
    public StringProperty estimatedTimeProperty() {
        return estimatedTime;
    }
    public StringProperty distanceProperty() {
        return distance;
    }
    public ReadOnlyBooleanProperty runningProperty() {
        return tourUpdateService.runningProperty();
    }

    public void setTourModel(TourModel selectedItem) {

        if (selectedItem == null) {
            isInitialValue = true;
        } else {
            this.data = selectedItem;
            isInitialValue = false;
        }
        setTourTabProperties();
    }

    private void setTourTabProperties() {
        if (this.isInitialValue) {
            title.setValue(null);
            description.setValue(null);
            detailsFrom.setValue(null);
            detailsTo.setValue(null);
            imageProperty.setValue(null);
            transportTypeProperty().setValue(null);
            estimatedTime.setValue(null);
            distance.setValue(null);
            logData.clear();
            popularity.setValue(null);
            childfriendliness.setValue(null);
        } else {
            title.setValue(data.getTitle());
            description.setValue(data.getDescription());
            detailsFrom.setValue(data.getFrom());
            detailsTo.setValue(data.getTo());
            transportTypeProperty().setValue(data.getTransportType());
            estimatedTime.setValue(data.getEstimatedTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            distance.setValue(String.format("%.1f", data.getTourDistance()) + "km");
            updateImage();
            updateTourLogData();
        }
    }

    public void updateTourModel(TourModel tourModel) {
        if(isInitialValue)
            return;
        data.setTitle(tourModel.getTitle());
        data.setDescription(tourModel.getDescription());
        data.setFrom(tourModel.getFrom());
        data.setTo(tourModel.getTo());
        data.setTransportType(tourModel.getTransportType());
        updateTourService();
    }

    private void waitToUpdateUi(){

        updateImage();
        this.title.setValue(data.getTitle());
        this.description.setValue(data.getDescription());
        this.detailsFrom.setValue(data.getFrom());
        this.detailsTo.setValue(data.getTo());
        this.transportTypeProperty().setValue(data.getTransportType());
        this.distance.setValue(String.format("%.1f", data.getTourDistance()) + "km");
        this.estimatedTime.setValue(data.getEstimatedTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        tourAppManager.calculateChildFriendliness(data);
        tourAppManager.calculatePopularity(data);
    }

    private void updateImage(){
        if(null != data.getImageFilename() && !data.getImageFilename().isEmpty()) {
            var path = AppConfigurationLoader.getInstance().getAppConfiguration().getImageFolder() +data.getImageFilename();
            Image image = new Image(new File(path).toURI().toString());
            imageProperty.setValue(image);
        }else{
            log.warn("Image could not be updated because filename was null.");
            imageProperty.setValue(null);
        }
    }

    public ObservableList<TourLog> getLogData() {
        return logData;
    }

    public void updateTourLogData() {
        logData.clear();
        logData.setAll(tourAppManager.getAllTourLogsForTour(data));

        childfriendliness.setValue(tourAppManager.calculateChildFriendliness(data));
        popularity.setValue(tourAppManager.calculatePopularity(data));
    }

    public void editTourLogData(TourLog tourLog) {
        var log = logData.stream().filter(x -> x.getLogId() == tourLog.getLogId()).findFirst().get();
        if(null != log) {
            log.setComment(tourLog.getComment());
            log.setDifficulty(tourLog.getDifficulty());
            log.setRating(tourLog.getRating());
            log.setTotalTime(tourLog.getTotalTime());
            tourAppManager.updateLog(log);
        }
    }

    public TourLog addNewLog() {
        var newItem = tourAppManager.createLog(data.getTourId());
        logData.add(0, newItem);
        return newItem;
    }

    public void deleteLog(TourLog tourItem) {
        tourAppManager.deleteLog(tourItem);
        logData.remove(tourItem);

        childfriendliness.setValue(tourAppManager.calculateChildFriendliness(data));
        popularity.setValue(tourAppManager.calculatePopularity(data));
    }

    public void updateTourService() {
        tourUpdateService.restart();
    }

    private void registerListenerForTourUpdateService(){
        tourUpdateService.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, newValue.getMessage());
                alert.showAndWait();
            }
        });
        tourUpdateService.runningProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue == false){
                waitToUpdateUi();
            }
        });
    }

    public class TourUpdateService extends Service<String> {
        protected Task<String> createTask() {
            return new Task<>() {
                protected String call() throws Exception {
                    tourAppManager.updateTour(data);
                    return "DONE";
                }
            };
        }
    }

}
