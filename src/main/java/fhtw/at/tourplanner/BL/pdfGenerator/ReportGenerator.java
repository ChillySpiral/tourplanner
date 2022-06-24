package fhtw.at.tourplanner.BL.pdfGenerator;

import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;

import java.util.List;

public interface ReportGenerator {
    public boolean generateReport(TourModel tour, List<TourLog> logs);
}
