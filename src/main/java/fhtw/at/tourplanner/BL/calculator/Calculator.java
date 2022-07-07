package fhtw.at.tourplanner.BL.calculator;

import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.enums.Difficulty;
import fhtw.at.tourplanner.DAL.model.enums.Rating;

import java.time.LocalTime;
import java.util.List;

public interface Calculator {
    LocalTime calculateAverageTime(List<LocalTime> times);
    Difficulty calculateAverageDifficulty(List<Difficulty> difficulties);
    Rating calculateAverageRating(List<Rating> ratings);
    String calculatePopularity(int allLogsSize, int tourLogSize);
    String calculateChildFriendliness(TourModel data, List<TourLog> tourLogs);

}
