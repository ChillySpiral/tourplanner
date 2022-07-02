package fhtw.at.tourplanner.DebugRunner;

import fhtw.at.tourplanner.BL.BLFactory;
import fhtw.at.tourplanner.DAL.model.enums.Difficulty;
import fhtw.at.tourplanner.DAL.model.enums.Rating;
import fhtw.at.tourplanner.DAL.model.enums.TransportType;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
//ToDo: Remove before release
public class BLRunner {

    public static void main(String[] args) {
        var test1 = false;
        var test2 = false;
        var test3 = false;
        var test4 = true;
        var test = BLFactory.getTourAppManager();
        if(test1) {


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

            test.generateTourReport(testTour, new File("./pdf/tourReport.pdf"));

            test.generateSummaryReport(new File("./pdf/SummaryReport.pdf"));
        }
        if(test2){
            var tour = test.getTour(1);
            test.exportTour(new File("./export/testTour.json"), tour);
        }
        if(test3){
            test.importTour(new File("./export/testTour.json"));
        }
        if(test4){
            test.searchTours("Expert");
        }
    }
}
