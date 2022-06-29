package fhtw.at.tourplanner.BL;

import fhtw.at.tourplanner.BL.jsonGenerator.JsonGenerator;
import fhtw.at.tourplanner.BL.jsonGenerator.JsonGeneratorImpl;
import fhtw.at.tourplanner.BL.pdfGenerator.implementation.ReportGeneratorImpl;
import fhtw.at.tourplanner.DAL.mapQuestAPI.implementation.MapQuestRepositoryImpl;

public final class TourAppManagerFactory {

    private static TourAppManager tourAM;

    public static TourAppManager getTourAppManager(){
        if(tourAM == null){
            tourAM = new TourAppManagerImpl(new ReportGeneratorImpl(), new MapQuestRepositoryImpl(), new JsonGeneratorImpl());
        }
        return tourAM;
    }
}
