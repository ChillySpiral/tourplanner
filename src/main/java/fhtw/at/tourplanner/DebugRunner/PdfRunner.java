package fhtw.at.tourplanner.DebugRunner;

import fhtw.at.tourplanner.BL.pdfGenerator.implementation.ReportGeneratorImpl;
import fhtw.at.tourplanner.Configuration.AppConfigurationLoader;
import fhtw.at.tourplanner.DAL.FileSystem.implementation.FileSystemImpl;
import fhtw.at.tourplanner.DAL.mapQuestAPI.implementation.MapQuestRepositoryImpl;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.enums.TransportType;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//ToDo: Remove before release

public class PdfRunner {
    public static void main(String[] args) {
        var testModel = new TourModel();
        testModel.setTourId(0);
        testModel.setFrom("Vienna");
        testModel.setTo("Salzburg");
        testModel.setTransportType(TransportType.Bicycle);
        testModel.setTitle("Wanderung abc");
        testModel.setTourDistance(2.0);
        testModel.setEstimatedTime(LocalTime.now());
        testModel.setDescription("This is a description to test the pdf generation. This is a description to test the pdf generation.This is a description to test the pdf generation.This is a description to test the pdf generation.This is a description to test the pdf generation.");
        List<TourLog> logs = new ArrayList<>();
        for(int i = 0; i<5; i++){
            var log = new TourLog();
            log.setComment("This is a comment for log number This is a comment for log number This is a comment for log number This is a comment for log number This is a comment for log number This is a comment for log number This is a comment for log number aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + i);
            log.setDateTime(LocalDateTime.now());
            log.setRating("Rating of " + i);
            log.setDifficulty("Difficulty abc " + i);
            log.setTotalTime(LocalTime.now());
            logs.add(log);
        }

        var pdfGenerator = new ReportGeneratorImpl(AppConfigurationLoader.getInstance().getAppConfiguration(), new FileSystemImpl(AppConfigurationLoader.getInstance().getAppConfiguration()));

        pdfGenerator.generateReport(testModel, logs, new File("./pdf/pdfRunner.pdf"));
    }
}
