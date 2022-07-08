package fhtw.at.tourplanner.DAL.model.mapQuestModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteError {

    @Getter
    @Setter
    private String errorCode;

    @Getter
    @Setter
    private String message;
}
