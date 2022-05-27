package fhtw.at.tourplanner.BL;

public final class BusinessLogicFactory {

    private static TourBusinessLogic tourBL;

    public static TourBusinessLogic GetFactoryBL(){
        if(tourBL == null){
            tourBL = new TourBusinessLogicImplementation();
        }
        return tourBL;
    }
}
