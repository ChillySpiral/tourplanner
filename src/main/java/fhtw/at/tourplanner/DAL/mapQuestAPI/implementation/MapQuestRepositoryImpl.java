package fhtw.at.tourplanner.DAL.mapQuestAPI.implementation;

import fhtw.at.tourplanner.DAL.DalFactory;
import fhtw.at.tourplanner.DAL.FileSystem.FileSystem;
import fhtw.at.tourplanner.DAL.helper.ConfigurationLoader;
import fhtw.at.tourplanner.DAL.mapQuestAPI.MapQuestRepository;
import fhtw.at.tourplanner.DAL.mapQuestAPI.MapQuestService;
import fhtw.at.tourplanner.DAL.mapQuestAPI.converter.TransportTypeConverter;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.mapQuestModels.MapQuestModel;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.Map;

public class MapQuestRepositoryImpl implements MapQuestRepository {

    private final MapQuestService service;
    private final FileSystem fileSystem;
    private final String mapQuestKey;
    private final String imagePath;

    public MapQuestRepositoryImpl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mapquestapi.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(MapQuestService.class);
        mapQuestKey = ConfigurationLoader.getConfig("MapQuestKey");
        imagePath = ConfigurationLoader.getConfig("ImageFolder");
        fileSystem = DalFactory.GetFileSystem();
    }

    @Override
    public MapQuestModel getRouteInfo(TourModel tourModel) {
        final MapQuestModel result;
        try{
            result = service.getRouteInfo(mapQuestKey, tourModel.getFrom(), tourModel.getTo(), "k", TransportTypeConverter.Convert(tourModel.getTransportType()).toString()).execute().body();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getRouteImage(TourModel tourModel) {
        final String result;
        try{
            var routeInfo = getRouteInfo(tourModel);
            if(routeInfo != null){
                var imageResponseBody = service.downloadImage(mapQuestKey, "640,480", routeInfo.getRoute().getSessionId(), routeInfo.getRoute().getBoundingBox().toString()).execute().body();
                var filename = "tourImage_" + tourModel.getTourId() + "_from_" + tourModel.getFrom() + "_to_" + tourModel.getTo() + ".jpeg";

                if(fileSystem.writeResponseBody(imageResponseBody, imagePath + filename)){
                    return imagePath + filename;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean checkIfRouteForImageChanged(TourModel tourModel){
        //ToDo: Implement
        return false;
    }
}
