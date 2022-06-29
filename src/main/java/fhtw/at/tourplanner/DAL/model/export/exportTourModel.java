package fhtw.at.tourplanner.DAL.model.export;

import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class exportTourModel{
    @Getter
    @Setter
    public TourModel tour;

    @Getter
    @Setter
    public List<TourLog> tourLogs;
}
