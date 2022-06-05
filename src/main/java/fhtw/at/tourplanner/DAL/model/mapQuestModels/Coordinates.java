package fhtw.at.tourplanner.DAL.model.mapQuestModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Coordinates {
    @Getter
    @Setter
    private String lng;

    @Getter
    @Setter
    private String lat;
}
