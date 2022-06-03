package fhtw.at.tourplanner.DAL.model;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MapQuestModel {
    @Getter
    @Setter
    private Route route;

    @Override
    public String toString(){
        return "Route: SessionId: " + route.getSessionId() + " Formatted Time: " + route.getFormattedTime() + " Distance: " +  route.getDistance();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Route {
        @Getter
        @Setter
        private String distance;
        @Getter
        @Setter
        private String formattedTime;
        @Getter
        @Setter
        private String sessionId;
    }

}



