package fhtw.at.tourplanner.DAL.model;

import fhtw.at.tourplanner.DAL.model.enums.TransportType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

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
    private TransportType transportType;

    @Getter
    @Setter
    private Double tourDistance;

    @Getter
    @Setter
    private LocalTime estimatedTime;

    @Getter
    @Setter
    private String imageFilename;

    @Override
    public String toString(){
        return title;
    }
}
