package fhtw.at.tourplanner.viewmodel;

import fhtw.at.tourplanner.BL.BLFactory;
import fhtw.at.tourplanner.BL.appManager.TourAppManager;
import fhtw.at.tourplanner.Configuration.AppConfigurationLoader;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
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
import java.time.format.DateTimeFormatter;

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

        String tmpFileName = data.getImageFilename() != null ? data.getImageFilename() : "";
        tourAppManager.updateTour(data);

        if(tmpFileName != data.getImageFilename()){
            updateImage();
        }

        this.title.setValue(data.getTitle());
        this.description.setValue(data.getDescription());
        this.detailsFrom.setValue(data.getFrom());
        this.detailsTo.setValue(data.getTo());
        this.transportTypeProperty().setValue(data.getTransportType());
        this.distance.setValue(String.format("%.1f", data.getTourDistance()) + "km");
        this.estimatedTime.setValue(data.getEstimatedTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
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

}
