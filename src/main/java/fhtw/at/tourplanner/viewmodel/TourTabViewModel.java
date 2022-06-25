package fhtw.at.tourplanner.viewmodel;

import fhtw.at.tourplanner.BL.TourAppManager;
import fhtw.at.tourplanner.BL.TourAppManagerFactory;
import fhtw.at.tourplanner.DAL.model.TourModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourTabViewModel {

    private TourModel data;
    private volatile boolean isInitialValue = false;
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty detailsFrom = new SimpleStringProperty();
    private final StringProperty detailsTo = new SimpleStringProperty();
    private final TourAppManager tourAppManager = TourAppManagerFactory.getTourAppManager();

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

    public void setTourModel(TourModel selectedItem) {

        if (selectedItem == null) {
            isInitialValue = true;
        } else {
            this.data = selectedItem;
            isInitialValue = false;
        }
        setTourTabProperties();
    }

    private void registerPropertyListeners() {
        title.addListener((arg, oldVal, newVal) -> updateTourModel());
        description.addListener((arg, oldVal, newVal) -> updateTourModel());
        detailsFrom.addListener((arg, oldVal, newVal) -> updateTourModel());
        detailsTo.addListener((arg, oldVal, newVal) -> updateTourModel());
        //ToDo: Alle Properties müssen hier registriert werden, sodass jede Änderung dieser auch das Model Updated
    }

    private void setTourTabProperties() {
        if (this.isInitialValue) {
            title.setValue(null);
            description.setValue(null);
            detailsFrom.setValue(null);
            detailsTo.setValue(null);
            //ToDo: Alle weiteren Properties müssen einen Initial-Wert bekommen
        } else {
            title.setValue(data.getTitle());
            description.setValue(data.getDescription());
            detailsFrom.setValue(data.getFrom());
            detailsTo.setValue(data.getTo());
            //ToDo: Alle weiteren Properties müssen hier gesetzt werden
        }
    }

    private void updateTourModel() {
        if(isInitialValue)
            return;
        data.setTitle(this.getTitle());
        data.setDescription(this.getDescription());
        data.setFrom(this.getDetailsFrom());
        data.setTo(this.getDetailsTo());
        tourAppManager.updateTour(data);
        //ToDo: Alle weiteren Property Updates müssen hier eingefügt werden
    }
}
