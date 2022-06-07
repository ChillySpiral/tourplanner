package fhtw.at.tourplanner.DAL.mapQuestAPI.implementation;

import fhtw.at.tourplanner.DAL.DalFactory;
import fhtw.at.tourplanner.DAL.FileSystem.FileSystem;
import fhtw.at.tourplanner.DAL.mapQuestAPI.MapQuestRepository;
import fhtw.at.tourplanner.DAL.mapQuestAPI.MapQuestService;
import fhtw.at.tourplanner.DAL.mapQuestAPI.converter.TransportTypeConverter;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.mapQuestModels.MapQuestModel;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

public class MapQuestRepositoryImpl implements MapQuestRepository {

    private final MapQuestService service;
    private final FileSystem fileSystem;

    public MapQuestRepositoryImpl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mapquestapi.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(MapQuestService.class);

        fileSystem = DalFactory.GetFileSystem();
    }

    @Override
    public MapQuestModel getRouteInfo(TourModel tourModel) {
        final MapQuestModel result;
        try{
            //ToDo: Key From Config
            result = service.getRouteInfo("<key>", tourModel.getFrom(), tourModel.getTo(), "k", TransportTypeConverter.Convert(tourModel.getTransportType()).toString()).execute().body();
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
                var imageResponseBody = service.downloadImage("<key>", "640,480", routeInfo.getRoute().getSessionId(), routeInfo.getRoute().getBoundingBox().toString()).execute().body();
                var path = "./img/"; //ToDo: From Config
                var filename = "tourImage" + tourModel.getTourId() + ".jpeg";

                if(fileSystem.writeResponseBody(imageResponseBody, path + filename)){
                    return path + filename;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
