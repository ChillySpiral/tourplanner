package fhtw.at.tourplanner.viewmodel;

import fhtw.at.tourplanner.BL.TourAppManager;
import fhtw.at.tourplanner.BL.TourAppManagerFactory;
import fhtw.at.tourplanner.DAL.helper.ConfigurationLoader;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TourTabViewModel {

    private TourModel data;
    private volatile boolean isInitialValue = false;
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty detailsFrom = new SimpleStringProperty();
    private final StringProperty detailsTo = new SimpleStringProperty();
    private final ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
    private final TourAppManager tourAppManager = TourAppManagerFactory.getTourAppManager();
    private final ObservableList<TourLog> logData = FXCollections.observableArrayList();


    public TourTabViewModel() {
        registerPropertyListeners();
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

    public void setTourModel(TourModel selectedItem) {

        if (selectedItem == null) {
            isInitialValue = true;
        } else {
            this.data = selectedItem;
            isInitialValue = false;
        }
        setTourTabProperties();
    }

    //ToDo: Marker: Manually trigger the update, since we use a dialog
    private void registerPropertyListeners() {
        /*
        title.addListener((arg, oldVal, newVal) -> updateTourModel());
        description.addListener((arg, oldVal, newVal) -> updateTourModel());
        detailsFrom.addListener((arg, oldVal, newVal) -> updateTourModel());
        detailsTo.addListener((arg, oldVal, newVal) -> updateTourModel());
         */
        //ToDo: Alle Properties müssen hier registriert werden, sodass jede Änderung dieser auch das Model Updated
    }

    private void setTourTabProperties() {
        if (this.isInitialValue) {
            title.setValue(null);
            description.setValue(null);
            detailsFrom.setValue(null);
            detailsTo.setValue(null);
            imageProperty.setValue(null);
            logData.clear();
            //ToDo: Alle weiteren Properties müssen einen Initial-Wert bekommen
        } else {
            title.setValue(data.getTitle());
            description.setValue(data.getDescription());
            detailsFrom.setValue(data.getFrom());
            detailsTo.setValue(data.getTo());
            updateImage();
            updateTourLogData();
            //ToDo: Alle weiteren Properties müssen hier gesetzt werden
        }
    }

    //ToDo: Marker: Add Paramter, set ViewModel Properties and data -> send to DB
    public void updateTourModel(TourModel tourModel) {
        if(isInitialValue)
            return;

        this.title.setValue(tourModel.getTitle());
        this.description.setValue(tourModel.getDescription());
        this.detailsFrom.setValue(tourModel.getFrom());
        this.detailsTo.setValue(tourModel.getTo());

        data.setTitle(this.getTitle());
        data.setDescription(this.getDescription());
        data.setFrom(this.getDetailsFrom());
        data.setTo(this.getDetailsTo());

        var tmpFileName = new String(data.getImageFilename());
        tourAppManager.updateTour(data);

        if(tmpFileName != data.getImageFilename()){
            updateImage();
        }
        //ToDo: Alle weiteren Property Updates müssen hier eingefügt werden
    }

    private void updateImage(){
        if(null != data.getImageFilename()) {
            var path = ConfigurationLoader.getConfig("ImageFolder")+data.getImageFilename();
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
            //log.setTotalTime(tourLog.getTotalTime());
            tourAppManager.updateLog(log);
        }
    }

    public void addNewLog() {
        var newItem = tourAppManager.createLog(data.getTourId());
        logData.add(newItem);
    }

    public void deleteLog(TourLog tourItem) {
        tourAppManager.deleteLog(tourItem);
        logData.remove(tourItem);
    }

}
