package fhtw.at.tourplanner.DAL.model.mapQuestModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BoundingBoxMapQuest {
    @Getter
    @Setter
    private Coordinates lr;

    @Getter
    @Setter
    private Coordinates ul;

    @Override
    public String toString(){
        return ul.getLat() + "," + ul.getLng() + "," + lr.getLat() + "," + lr.getLng();
    }
}

