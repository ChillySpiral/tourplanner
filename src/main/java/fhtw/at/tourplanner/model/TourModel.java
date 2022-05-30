package fhtw.at.tourplanner.model;

import lombok.Getter;
import lombok.Setter;

public class TourModel {

    @Getter
    @Setter
    private String title = new String();

    @Getter
    @Setter
    private String description = new String();
    //Route
    //List<Log>

    public TourModel(String title, String description){
        this.title = title;
        this.description = description;
    }

    public TourModel(){
    }

    @Override
    public String toString(){
        return title;
    }
}
