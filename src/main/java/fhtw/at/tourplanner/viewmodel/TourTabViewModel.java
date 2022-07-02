package fhtw.at.tourplanner.viewmodel;

import fhtw.at.tourplanner.BL.BLFactory;
import fhtw.at.tourplanner.BL.appManager.TourAppManager;
import fhtw.at.tourplanner.BL.pdfGenerator.helper.Calculator;
import fhtw.at.tourplanner.Configuration.AppConfigurationLoader;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.enums.Difficulty;
import fhtw.at.tourplanner.DAL.model.enums.TransportType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import lombok.Getter;

import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
    private final TourAppManager tourAppManager = BLFactory.getTourAppManager();
    private final ObservableList<TourLog> logData = FXCollections.observableArrayList();

    private final StringProperty popularity = new SimpleStringProperty();
    private final StringProperty childfriendliness = new SimpleStringProperty();


    public TourTabViewModel() {
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

    public ObjectProperty<TransportType> transportTypeProperty() {
        return transportType;
    }
    public StringProperty estimatedTimeProperty() {
        return estimatedTime;
    }
    public StringProperty distanceProperty() {
        return distance;
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
            calculatePopularity();
            calculateChildfriendliness();
        }
    }

    public void updateTourModel(TourModel tourModel) {
        if(isInitialValue)
            return;

        this.title.setValue(tourModel.getTitle());
        this.description.setValue(tourModel.getDescription());
        this.detailsFrom.setValue(tourModel.getFrom());
        this.detailsTo.setValue(tourModel.getTo());
        this.transportTypeProperty().setValue(tourModel.getTransportType());

        data.setTitle(this.getTitle());
        data.setDescription(this.getDescription());
        data.setFrom(this.getDetailsFrom());
        data.setTo(this.getDetailsTo());
        data.setTransportType(this.getTransportType());

        String tmpFileName = data.getImageFilename() != null ? data.getImageFilename() : "";
        tourAppManager.updateTour(data);

        if(tmpFileName != data.getImageFilename()){
            updateImage();
        }
    }

    private void updateImage(){
        if(null != data.getImageFilename()) {
            var path = AppConfigurationLoader.getInstance().getAppConfiguration().getImageFolder() +data.getImageFilename();
            Image image = new Image(new File(path).toURI().toString());
            imageProperty.setValue(image);
        }else{
            imageProperty.setValue(null);
        }
    }

    public ObservableList<TourLog> getLogData() {
        return logData;
    }

    public void updateTourLogData() {
        logData.clear();
        logData.setAll(tourAppManager.getAllTourLogsForTour(data));
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
    }

    public void calculatePopularity() {

    }

    public void calculateChildfriendliness() {

        List<TourLog> allLogs = tourAppManager.getAllTourLogs();
        Difficulty averageDifficulty = Calculator.calculateAverageDifficulty(allLogs.stream().map(TourLog::getDifficulty).collect(Collectors.toList()));
        LocalTime averageDuration = Calculator.calculateAverageTime(allLogs.stream().map(TourLog::getTotalTime).collect(Collectors.toList()));
        double distance = data.getTourDistance();
        TransportType transport = data.getTransportType();

        //int num = averageDuration.getHour()*60 + averageDuration.getMinute();
        //ToDo: Some marker with x points, for every transport type and factor(difficulty, time, distance) there is a threashold, if it gets over there x points deduction
        //ToDo: The endpoints after all the factor checks, results in: Not child-friendly, neutral or child friendly

        if(TransportType.Car == transport) {

        }
        else if(TransportType.Bicycle == transport) {

        }
        else { // Foot

        }



    }

}
