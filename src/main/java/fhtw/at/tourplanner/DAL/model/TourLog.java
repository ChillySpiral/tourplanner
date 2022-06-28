package fhtw.at.tourplanner.DAL.model;

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
    private String difficulty; //ToDo: Enum

    @Getter
    @Setter
    private LocalTime totalTime;

    @Getter
    @Setter
    private String rating; //ToDo: Enum

    @Getter
    @Setter
    private int tourId;

}
