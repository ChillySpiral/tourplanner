package fhtw.at.tourplanner.BL;

import fhtw.at.tourplanner.BL.pdfGenerator.implementation.ReportGeneratorImpl;

public final class TourAppManagerFactory {

    private static TourAppManager tourAM;

    public static TourAppManager getTourAppManager(){
        if(tourAM == null){
            tourAM = new TourAppManagerImpl(new ReportGeneratorImpl());
        }
        return tourAM;
    }
}
