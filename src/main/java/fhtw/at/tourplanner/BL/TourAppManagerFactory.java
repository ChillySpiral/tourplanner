package fhtw.at.tourplanner.BL;

public final class TourAppManagerFactory {

    private static TourAppManager tourAM;

    public static TourAppManager getTourAppManager(){
        if(tourAM == null){
            tourAM = new TourAppManagerImpl();
        }
        return tourAM;
    }
}
