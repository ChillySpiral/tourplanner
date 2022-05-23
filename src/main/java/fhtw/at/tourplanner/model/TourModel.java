package fhtw.at.tourplanner.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourModel {
    private String title = new String();
    private String description = new String();
    //Route
    //List<Log>

    public TourModel(){
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String newTitle){
        title = newTitle;
    }
    public String getDescription() {
        return description;
    }

    @Override
    public String toString(){
        return title;
    }
}
