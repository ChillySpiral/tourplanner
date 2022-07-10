package fhtw.at.tourplanner.PL.viewmodel;

import fhtw.at.tourplanner.BL.appManager.TourAppManager;
import fhtw.at.tourplanner.Configuration.AppConfigurationLoader;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.enums.TransportType;
import fhtw.at.tourplanner.DAL.model.weatherModel.Current;
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
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

@Log4j2
public class TourTabViewModel {

    @Getter
    private TourModel data;
    private Current weatherInfo;
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
    private final StringProperty childFriendliness = new SimpleStringProperty();
    private final StringProperty weatherTemperature = new SimpleStringProperty();
    private final StringProperty weatherCondition = new SimpleStringProperty();
    private final TourUpdateService tourUpdateService;
    private final TourWeatherService tourWeatherService;

    public TourTabViewModel(TourAppManager tourAppManager) {
        this.tourAppManager = tourAppManager;
        this.tourUpdateService = new TourUpdateService();
        this.tourWeatherService = new TourWeatherService();
        registerListenerForTourUpdateService();
        registerListenerForTourWeatherService();
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
    public StringProperty childFriendlinessProperty() {
        return childFriendliness;
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
    public StringProperty weatherTemperature(){
        return weatherTemperature;
    }
    public StringProperty weatherCondition(){
        return weatherCondition;
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
            weatherCondition.setValue(null);
            weatherTemperature.setValue(null);
            popularity.setValue(null);
            childFriendliness.setValue(null);
        } else {
            title.setValue(data.getTitle());
            description.setValue(data.getDescription());
            detailsFrom.setValue(data.getFrom());
            detailsTo.setValue(data.getTo());
            transportTypeProperty().setValue(data.getTransportType());
            estimatedTime.setValue(data.getEstimatedTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            distance.setValue(String.format("%.1f", data.getTourDistance()) + "km");
            weatherCondition.setValue(null);
            weatherTemperature.setValue(null);
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

    public void checkWeather(){
        log.info("Started weather checking for destination [name: "+data.getTo()+"]");
        updateWeatherService();
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

        this.weatherInfo = null;
        this.weatherTemperature.setValue("");
        this.weatherCondition.setValue("");

        tourAppManager.calculateChildFriendliness(data);
        tourAppManager.calculatePopularity(data);
    }
    private void waitToUpdateWeather(){
        if(weatherInfo != null){
            this.weatherCondition.setValue(weatherInfo.getCondition().getText());
            this.weatherTemperature.setValue(String.valueOf(weatherInfo.getTemp_c())+" \u00b0C");
        }
    }

    private void updateImage(){
        if(null != data.getImageFilename() && !data.getImageFilename().isEmpty()) {
            var path = AppConfigurationLoader.getInstance().getAppConfiguration().getImageFolder() +data.getImageFilename();
            Image image = new Image(new File(path).toURI().toString());
            imageProperty.setValue(image);
        }else{
            log.info("Image could not be updated because filename was null.");
            imageProperty.setValue(null);
        }
    }

    public ObservableList<TourLog> getLogData() {
        return logData;
    }

    public void updateTourLogData() {
        logData.clear();
        var logs = tourAppManager.getAllTourLogsForTour(data);
        logs.sort(Comparator.comparing(TourLog::getLogId).reversed());
        logData.setAll(logs);

        childFriendliness.setValue(tourAppManager.calculateChildFriendliness(data));
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

        childFriendliness.setValue(tourAppManager.calculateChildFriendliness(data));
        popularity.setValue(tourAppManager.calculatePopularity(data));
    }

    public void updateTourService() {
        tourUpdateService.restart();
    }
    public void updateWeatherService(){
        tourWeatherService.restart();
    }

    private void registerListenerForTourUpdateService(){
        tourUpdateService.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, newValue.getMessage());
                alert.showAndWait();
            }
        });
        tourUpdateService.runningProperty().addListener((observable, oldValue, newValue) ->{
            if(!newValue){
                waitToUpdateUi();
            }
        });
    }
    private void registerListenerForTourWeatherService(){
        tourWeatherService.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, newValue.getMessage());
                alert.showAndWait();
            }
        });
        tourWeatherService.runningProperty().addListener((observable, oldValue, newValue) ->{
            if(!newValue){
                waitToUpdateWeather();
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

    public class TourWeatherService extends Service<String> {
        protected Task<String> createTask() {
            return new Task<>() {
                protected String call() throws Exception {
                    log.info("Starting Weather task for tour [id: "+data.getTourId()+" ]");
                    var result = tourAppManager.getWeatherInfo(data);
                    weatherInfo = result;
                    return "DONE";
                }
            };
        }
    }

}
