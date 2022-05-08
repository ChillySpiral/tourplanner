package fhtw.at.tourplanner.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourModel {

    public StringProperty title = new SimpleStringProperty();
    public StringProperty description = new SimpleStringProperty();
    //Route
    //List<Log>

    public TourModel(String title, String description){
        this.title.setValue(title);
        this.description.setValue(description);
    }

    @Override
    public String toString() {
        return this.title.getValue();
    }
}
