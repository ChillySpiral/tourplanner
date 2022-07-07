package fhtw.at.tourplanner.BL.appManager;

import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;

import java.io.File;
import java.util.List;

public interface TourAppManager {
    List<TourModel> getAllTours();
    TourModel getTour(int Id);
    TourModel createTour();
    void deleteTour(TourModel tourModel);
    void updateTour(TourModel tourModel);
    List<TourLog> getAllTourLogsForTour(TourModel tourModel);
    List<TourLog> getAllTourLogs();
    TourLog createLog(int tourId);
    void deleteLog(TourLog log);
    void updateLog(TourLog log);
    void generateTourReport(TourModel tourModel, File pdfFile);
    void generateSummaryReport(File pdfFile);
    void exportTour(File exportFile, TourModel tourModel);
    TourModel importTour(File importFile);
    List<TourModel> searchTours(String searchText);
    String calculatePopularity(TourModel tourModel);
    String calculateChildFriendliness(TourModel tourModel);
}
