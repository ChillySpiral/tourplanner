package fhtw.at.tourplanner.DAL.model.weatherModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Current {

    @Setter
    @Getter
    private double temp_c;

    @Setter
    @Getter
    private Condition condition;

    @Override
    public String toString(){
        return "Temperature: " + temp_c + " Condition: " + condition.getText();
    }
}
