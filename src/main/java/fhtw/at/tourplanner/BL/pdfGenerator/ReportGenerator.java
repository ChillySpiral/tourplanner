package fhtw.at.tourplanner.BL.pdfGenerator;

import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.fileSystem.Pair;

import java.io.File;
import java.util.List;

public interface ReportGenerator {
    public boolean generateReport(TourModel tour, List<TourLog> logs, File pdfFile);

    public boolean generateSummary(List<Pair<TourModel, List<TourLog>>> allTours, File pdfFile);
}
