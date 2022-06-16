package fhtw.at.tourplanner.DAL.mapQuestAPI;

import fhtw.at.tourplanner.DAL.mapQuestAPI.implementation.MapQuestRepositoryImpl;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.enums.TransportType;

/**
 * This is only to be used to test the Implementation of the MapQuestApi
 * Do not use this to run the program
 * ToDo: Remove before release
 */


public class TestMapQuestApiRunner {
    public static void main(String[] args) {
        var test = new MapQuestRepositoryImpl();
        var testModel = new TourModel();
        testModel.setFrom("Vienna");
        testModel.setTo("Salzburg");
        testModel.setTransportType(TransportType.Bicycle);
        //var result = test.getRouteInfo(testModel);
        //System.out.println(result);
        test.getRouteImage(testModel);
    }
}
