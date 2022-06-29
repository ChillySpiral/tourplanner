package fhtw.at.tourplanner.BL;

import fhtw.at.tourplanner.DAL.model.enums.Difficulty;
import fhtw.at.tourplanner.DAL.model.enums.Rating;
import fhtw.at.tourplanner.DAL.model.enums.TransportType;

import java.time.LocalDateTime;
import java.time.LocalTime;
//ToDo: Remove before release
public class BLRunner {

    public static void main(String[] args) {

        var test = TourAppManagerFactory.getTourAppManager();
        var testTour = test.getTour(1);
        testTour.setTo("Zagreb");
        test.updateTour(testTour);

        var log = test.createLog(1);
        log.setDateTime(LocalDateTime.now());
        log.setDifficulty(Difficulty.Advanced);
        log.setRating(Rating.Good);
        log.setComment("This is a test comment to test the pdf generation");
        log.setTotalTime(LocalTime.MAX);
        test.updateLog(log);

        test.generateTourReport(testTour);

        test.generateSummaryReport();
    }
}
