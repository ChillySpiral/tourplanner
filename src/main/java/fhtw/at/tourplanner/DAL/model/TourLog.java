package fhtw.at.tourplanner.DAL.model;

import fhtw.at.tourplanner.DAL.model.enums.Difficulty;
import fhtw.at.tourplanner.DAL.model.enums.Rating;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
public class TourLog {

    @Getter
    @Setter
    private int logId;

    @Getter
    @Setter
    private LocalDateTime dateTime;

    @Getter
    @Setter
    private String comment;

    @Getter
    @Setter
    private Difficulty difficulty;

    @Getter
    @Setter
    private LocalTime totalTime;

    @Getter
    @Setter
    private Rating rating;

    @Getter
    @Setter
    private int tourId;

}
