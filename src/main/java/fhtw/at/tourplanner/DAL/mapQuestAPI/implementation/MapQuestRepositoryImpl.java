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
            log.error("Map Quest Get Route for tour [id: "+tourModel.getTourId()+"] failed [error: "+e.getMessage()+"]");
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
            log.error("Issues with MapQuest Query [Error:"+e.getMessage()+ "]");
            return new Pair<>(e.getMessage(), null);
        }
        return null;
    }

    private Pair<String, Boolean> checkIfRouteSuccessful(MapQuestModel route) {
        if(route != null) {
            if (route.getRoute() != null) {
                if (route.getRoute().getRouteError().getErrorCode().equals("-400")) {
                    return new Pair<>(null, true);
                } else{
                    var routeErrorMessage = route.getRoute().getRouteError().getMessage();
                    var routeInfoMessage = route.getInfo().getMessages();

                    if(!routeErrorMessage.isEmpty())
                        routeInfoMessage.add(routeErrorMessage);

                    if(!routeInfoMessage.isEmpty()) {
                        String breaker = "\n";
                        StringBuilder sb = new StringBuilder();

                        int i = 0;
                        while (i < routeInfoMessage.size() - 1) {
                            sb.append(routeInfoMessage.get(i));
                            sb.append(breaker);
                            i++;
                        }
                        sb.append(routeInfoMessage.get(i));
                        String result = sb.toString();

                        return new Pair<>(result, false);
                    }
                }
            }
        }
        return new Pair<>("Unknown Route Issue", false);

    }
}
