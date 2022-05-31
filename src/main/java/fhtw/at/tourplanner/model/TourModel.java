package fhtw.at.tourplanner.model;

import fhtw.at.tourplanner.model.enums.RouteType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;


//Properties might need to be redefined to match MapQuest API

@AllArgsConstructor
@NoArgsConstructor
public class TourModel {

    @Getter
    @Setter
    private int tourId;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String from;

    @Getter
    @Setter
    private String to;

    @Getter
    @Setter
    private RouteType routeType; //Enum

    @Getter
    @Setter
    private Double tourDistance;

    @Getter
    @Setter
    private LocalTime estimatedTime; //Questionable? maybe other data type

    @Getter
    @Setter
    private String imageFilename;

    @Override
    public String toString(){
        return title;
    }
}
