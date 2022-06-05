package fhtw.at.tourplanner.DAL.mapQuestAPI.implementation;

import fhtw.at.tourplanner.DAL.model.mapQuestModels.MapQuestModel;
import fhtw.at.tourplanner.DAL.mapQuestAPI.MapQuestRepository;
import fhtw.at.tourplanner.DAL.mapQuestAPI.MapQuestService;
import fhtw.at.tourplanner.DAL.mapQuestAPI.converter.TransportTypeConverter;
import fhtw.at.tourplanner.DAL.model.TourModel;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.*;

public class MapQuestRepositoryImpl implements MapQuestRepository {

    private final MapQuestService service;

    public MapQuestRepositoryImpl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mapquestapi.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(MapQuestService.class);
    }

    @Override
    public MapQuestModel getRouteInfo(TourModel tourModel) {
        final MapQuestModel result;
        try{
            //ToDo: Key From Config
            result = service.getRouteInfo("<Key>", tourModel.getFrom(), tourModel.getTo(), "k", TransportTypeConverter.Convert(tourModel.getTransportType()).toString()).execute().body();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    //ToDo: Restructure and File access
    @Override
    public String getRouteImage(TourModel tourModel) {
        final String result;
        try{
            var routeInfo = getRouteInfo(tourModel);
            if(routeInfo != null){
                var imageResponseBody = service.downloadImage("<Key>", "640,480", routeInfo.getRoute().getSessionId(), routeInfo.getRoute().getBoundingBox().toString()).execute().body();
                if(writeResponseBody(imageResponseBody, "J:/Christian/projects/tmp/test.jpeg")){
                    return "success";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "failed";
    }

    public static boolean writeResponseBody(ResponseBody body, String path) {
        try {

            File file = new File(path);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
