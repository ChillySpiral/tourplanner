package fhtw.at.tourplanner.viewmodel;

import fhtw.at.tourplanner.model.TourModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourTabViewModel {

    private TourModel data;
    private volatile boolean isInitialValue = false;
    private final StringProperty title = new SimpleStringProperty();

    public TourTabViewModel() {
        registerPropertyListeners();
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
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
        //ToDo: Alle Properties müssen hier registriert werden, sodass jede Änderung dieser auch das Model Updated
    }

    private void setTourTabProperties() {
        if (this.isInitialValue) {
            title.setValue("");
            //ToDo: Alle weiteren Properties müssen einen Initial-Wert bekommen
        } else {
            title.setValue(data.getTitle());
            //ToDo: Alle weiteren Properties müssen hier gesetzt werden
        }
    }

    private void updateTourModel() {
        if(isInitialValue)
            return;
        data.setTitle(this.getTitle());
        //ToDo: Alle weiteren Property Updates müssen hier eingefügt werden
    }
}
