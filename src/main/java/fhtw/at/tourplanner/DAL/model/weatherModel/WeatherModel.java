package fhtw.at.tourplanner.DAL.model.weatherModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherModel {

    @Setter
    @Getter
    private Current current;
}
