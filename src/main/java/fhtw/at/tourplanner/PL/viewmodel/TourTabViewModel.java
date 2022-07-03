package fhtw.at.tourplanner.PL.viewmodel;

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


    public TourTabViewModel(TourAppManager tourAppManager) {
        this.tourAppManager = tourAppManager;
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

        calculateChildfriendliness();
        calculatePopularity();
    }

    private void updateImage(){
        if(null != data.getImageFilename()) {
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

        calculateChildfriendliness();
        calculatePopularity();
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

        calculatePopularity();
        calculateChildfriendliness();
    }

    public void calculatePopularity() {
        List<TourLog> allLogs = tourAppManager.getAllTourLogs();
        List<TourLog> myLogs = tourAppManager.getAllTourLogsForTour(data);
        double percentage;

        if(allLogs.isEmpty()) {
            log.warn("Could not calculate popularity because there are no logs in existence.");
            percentage = 0;
        }
        else
            percentage = (double) myLogs.size()/allLogs.size();

        if(myLogs.isEmpty() || 0 == percentage)
            popularity.setValue("Not popular.");
        else if(0.5 < percentage)
            popularity.setValue("The most popular! Over 50 % of all tour logs are for this tour.");
        else if(0.5 == percentage)
            popularity.setValue("Very popular! 50 % of tour all logs are for this tour.");
        else if(0.3333 <= percentage)
            popularity.setValue("Very popular! Over 33.33 % of all tour logs are for this tour.");
        else if(0.25 <= percentage)
            popularity.setValue("Very popular! Over 25 % of all tour logs are for this tour.");
        else if(0.2 <= percentage)
            popularity.setValue("Fairly popular! Over 20 % of all tour logs are for this tour.");
        else if(0.1 <= percentage)
            popularity.setValue("Somewhat popular. Over 10 % of all tour logs are for this tour.");
        else if(0.05 <= percentage)
            popularity.setValue("Slightly popular. Over 5 % of all tour logs are for this tour.");
        else
            popularity.setValue("Not very popular. Less than 5 % of all tour logs are for this tour.");

    }

    public void calculateChildfriendliness() {
        if(data == null){
            log.warn("No tour data available. Data is null");
            return;
        }

        List<TourLog> allLogs = tourAppManager.getAllTourLogsForTour(data);
        if(null == allLogs || allLogs.isEmpty()) {
            log.warn("Could not calculate child-friendliness because there exist no logs for this tour. [ tourId: " + data.getTourId() + " ]");
            childfriendliness.setValue("Not enough data.");
            return;
        }

        Difficulty averageDifficulty = Calculator.calculateAverageDifficulty(allLogs.stream().map(TourLog::getDifficulty).collect(Collectors.toList()));
        LocalTime averageDuration = Calculator.calculateAverageTime(allLogs.stream().map(TourLog::getTotalTime).collect(Collectors.toList()));

        if(null == averageDifficulty || null == averageDuration) {
            log.warn("Could not calculate child-friendliness because average difficulty or average duration did not return any values. [ tourId: " + data.getTourId() + " ]");
            childfriendliness.setValue("Not enough data.");
            return;
        }

        double distance = data.getTourDistance();
        int durationMinutes = averageDuration.getMinute()+averageDuration.getHour()*60;
        TransportType transport = data.getTransportType();

        int friendlinessLevel = 6;

        if(TransportType.Car == transport) {
            if(350 < distance)
                friendlinessLevel--;
            if(600 < distance)
                friendlinessLevel--;

            if(Difficulty.Intermediate == averageDifficulty)
                friendlinessLevel--;
            else if(Difficulty.Expert == averageDifficulty)
                friendlinessLevel-=2;

            if(5*60 < durationMinutes)
                friendlinessLevel--;
            if(8*60 < durationMinutes)
                friendlinessLevel--;
        }
        else if(TransportType.Bicycle == transport) {
            //
            if(30 < distance)
                friendlinessLevel--;
            if(60 < distance)
                friendlinessLevel--;

            if(Difficulty.Intermediate == averageDifficulty)
                friendlinessLevel--;
            else if(Difficulty.Expert == averageDifficulty)
                friendlinessLevel-=2;

            if(3*60 < durationMinutes)
                friendlinessLevel--;
            if(6*60 < durationMinutes)
                friendlinessLevel--;
        }
        else { // Foot
            // max 10km
            if(10 < distance)
                friendlinessLevel--;
            if(20 < distance)
                friendlinessLevel--;

            if(Difficulty.Intermediate == averageDifficulty)
                friendlinessLevel--;
            else if(Difficulty.Expert == averageDifficulty)
                friendlinessLevel-=2;

            if(4*60 < durationMinutes)
                friendlinessLevel--;
            if(8*60 < durationMinutes)
                friendlinessLevel--;
        }

        switch(friendlinessLevel){
            case 6:
            case 5: childfriendliness.setValue("Child friendly.");
                    break;
            case 4:
            case 3: childfriendliness.setValue("For advanced or older children.");
                    break;
            case 2:
            case 1:
            case 0: childfriendliness.setValue("Not suitable for children.");
                    break;

        }

    }

}
