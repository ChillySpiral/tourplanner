package fhtw.at.tourplanner.DAL.model.mapQuestModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

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

    @Getter
    @Setter
    private BoundingBoxMapQuest boundingBox;
}
