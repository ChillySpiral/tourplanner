package fhtw.at.tourplanner.DAL.model.mapQuestModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Info {
    @Getter
    @Setter
    private String statuscode;

    @Getter
    @Setter
    private List<String> messages;
}
