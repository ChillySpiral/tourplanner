package fhtw.at.tourplanner.DAL.model.mapQuestModels;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MapQuestModel {
    @Getter
    @Setter
    private Route route;

    @Getter
    @Setter
    private Info info;

    @Override
    public String toString() {
        return "Route: SessionId: " + route.getSessionId() + " Formatted Time: " + route.getFormattedTime() + " Distance: " + route.getDistance();
    }
}





