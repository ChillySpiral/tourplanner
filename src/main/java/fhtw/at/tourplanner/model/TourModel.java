package fhtw.at.tourplanner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;


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
    private String transportType; //Enum

    @Getter
    @Setter
    private Double tourDistance;

    @Getter
    @Setter
    private LocalTime estimatedTime; //Questionable? maybe other data type

    @Getter
    @Setter
    private int idToRouteImage;

    @Override
    public String toString(){
        return title;
    }
}
