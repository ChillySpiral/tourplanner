package fhtw.at.tourplanner.viewmodel.dialog;

import fhtw.at.tourplanner.DAL.model.enums.TransportType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourEditViewModel {
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty from = new SimpleStringProperty();
    private final StringProperty to = new SimpleStringProperty();
    private final ObjectProperty<TransportType> transportType = new SimpleObjectProperty<>();

    public TourEditViewModel(String title, String description, String from, String to, TransportType transportType){
        this.title.setValue(title);
        this.description.setValue(description);
        this.from.setValue(from);
        this.to.setValue(to);
        this.transportType.setValue(transportType);
    }

    public String getTitle() {
        return title.get();
    }
    public StringProperty titleProperty() {
        return title;
    }

    public String getDescription() {
        return description.get();
    }
    public StringProperty descriptionProperty() {
        return description;
    }

    public String getFrom() {
        return from.get();
    }
    public StringProperty fromProperty() {
        return from;
    }

    public String getTo() {
        return to.get();
    }
    public StringProperty toProperty() {
        return to;
    }

    public ObjectProperty<TransportType> transportTypeProperty() {
        return transportType;
    }

    public TransportType getTransportType() {
        return transportType.getValue();
    }

    @Override
    public String toString(){
        return getTitle() + " " + getDescription() + " " + getFrom() + " " + getTo();
    }
}
