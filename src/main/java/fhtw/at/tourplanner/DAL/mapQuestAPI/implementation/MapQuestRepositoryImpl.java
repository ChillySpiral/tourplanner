package fhtw.at.tourplanner.DAL.mapQuestAPI.implementation;

import fhtw.at.tourplanner.DAL.DalFactory;
import fhtw.at.tourplanner.DAL.FileSystem.FileSystem;
import fhtw.at.tourplanner.DAL.helper.ConfigurationLoader;
import fhtw.at.tourplanner.DAL.mapQuestAPI.MapQuestRepository;
import fhtw.at.tourplanner.DAL.mapQuestAPI.MapQuestService;
import fhtw.at.tourplanner.DAL.mapQuestAPI.converter.TransportTypeConverter;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.fileSystem.Pair;
import fhtw.at.tourplanner.DAL.model.mapQuestModels.MapQuestModel;
import fhtw.at.tourplanner.DAL.model.mapQuestModels.Route;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

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
    public Pair<String, Route> getRouteImage(TourModel tourModel) {
        try{
            var routeInfo = getRouteInfo(tourModel);
            if(checkIfRouteSuccessful(routeInfo)){
                var imageResponseBody = service.downloadImage(mapQuestKey, "640,480", routeInfo.getRoute().getSessionId(), routeInfo.getRoute().getBoundingBox().toString()).execute().body();
                var filename = "tourImage_" + tourModel.getTourId() +"_.jpeg";

                if(fileSystem.writeResponseBody(imageResponseBody, filename)){
                    return new Pair<>(filename, routeInfo.getRoute());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean checkIfRouteSuccessful(MapQuestModel route){
        if(route != null && route.getRoute().getSessionId() != null){
            return true;
        }
        var errorMessage = route.getInfo().getMessages();

        String breaker = "\n";
        StringBuilder sb = new StringBuilder();

        int i = 0;
        while (i < errorMessage.size() - 1)
        {
            sb.append(errorMessage.get(i));
            sb.append(breaker);
            i++;
        }
        sb.append(errorMessage.get(i));
        String result = sb.toString();

        throw new RuntimeException(result);
    }
}
