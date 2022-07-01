package fhtw.at.tourplanner.DebugRunner;

import fhtw.at.tourplanner.Configuration.AppConfigurationLoader;
import fhtw.at.tourplanner.DAL.FileSystem.implementation.FileSystemImpl;
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
        var test = new MapQuestRepositoryImpl(AppConfigurationLoader.getInstance().getAppConfiguration(), new FileSystemImpl(AppConfigurationLoader.getInstance().getAppConfiguration()));
        var testModel = new TourModel();
        testModel.setFrom("Vienna");
        testModel.setTo("Berlin");
        testModel.setTransportType(TransportType.Bicycle);
        //var result = test.getRouteInfo(testModel);
        //System.out.println(result);
        test.getRouteImage(testModel);
    }
}
