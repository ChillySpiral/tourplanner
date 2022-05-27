package fhtw.at.tourplanner.DAL;

public class DataLogicFactory {
    private static TourDataLogic tourDL;

    public static TourDataLogic GetFactoryDL(){
        if(tourDL == null){
            tourDL = new TourDataLogicInMemory();
        }
        return tourDL;
    }
}
