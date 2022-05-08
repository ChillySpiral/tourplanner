package fhtw.at.tourplanner.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourModel {
    private StringProperty title = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    //Route
    //List<Log>

    public TourModel(String title, String description){
        this.title.setValue(title);
        this.description.setValue(description);
    }

    public StringProperty getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return this.title.getValue();
    }
}
