package fhtw.at.tourplanner.DAL.mapQuestAPI.implementation;

import fhtw.at.tourplanner.Configuration.AppConfiguration;
import fhtw.at.tourplanner.DAL.FileSystem.FileSystem;
import fhtw.at.tourplanner.DAL.mapQuestAPI.MapQuestRepository;
import fhtw.at.tourplanner.DAL.mapQuestAPI.MapQuestService;
import fhtw.at.tourplanner.DAL.mapQuestAPI.converter.TransportTypeConverter;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.fileSystem.Pair;
import fhtw.at.tourplanner.DAL.model.mapQuestModels.MapQuestModel;
import fhtw.at.tourplanner.DAL.model.mapQuestModels.Route;
import lombok.extern.log4j.Log4j2;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

@Log4j2
public class MapQuestRepositoryImpl implements MapQuestRepository {

    private final MapQuestService service;
    private final FileSystem fileSystem;
    private final String mapQuestKey;
    private final String imagePath;

    public MapQuestRepositoryImpl(AppConfiguration appConfiguration, FileSystem fileSystem){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mapquestapi.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(MapQuestService.class);
        mapQuestKey = appConfiguration.getApiKey();
        imagePath = appConfiguration.getImageFolder();
        this.fileSystem = fileSystem;
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
            var routeInfoChecked = checkIfRouteSuccessful(routeInfo);
            if(routeInfoChecked.bObject){
                var imageResponseBody = service.downloadImage(mapQuestKey, "800,600", routeInfo.getRoute().getSessionId(), routeInfo.getRoute().getBoundingBox().toString()).execute().body();
                var filename = "tourImage_" + tourModel.getTourId() +"_.jpeg";

                if(fileSystem.writeResponseBody(imageResponseBody, filename)){
                    return new Pair<>(filename, routeInfo.getRoute());
                }
            } else{
                return new Pair<>(routeInfoChecked.aObject, null);
            }
        } catch (IOException e) {
            log.warn("Issues with MapQuest Query [Error:"+e.getMessage()+ "]");
            e.printStackTrace();
            return new Pair<>(e.getMessage(), null);
        }
        return null;
    }

    private Pair<String, Boolean> checkIfRouteSuccessful(MapQuestModel route) {
        if(route != null && route.getRoute().getSessionId() != null){
            if(!route.getRoute().getFormattedTime().isEmpty()){
                var split = route.getRoute().getFormattedTime().split(":");
                var hours = Integer.parseInt(split[0]);
                if(hours > 23) {
                    return new Pair<>("Exceeding Tour Plan Timelimit of 23:59:59", false);
                }
            }
            return new Pair<>("",true);
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

        return new Pair<>(result, false);
    }
}
