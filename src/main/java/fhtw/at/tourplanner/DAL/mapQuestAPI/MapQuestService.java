package fhtw.at.tourplanner.DAL.mapQuestAPI;

import fhtw.at.tourplanner.DAL.model.mapQuestModels.MapQuestModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface MapQuestService {

    @GET("directions/v2/route")
    Call<MapQuestModel> getRouteInfo(
            @Query("key") String key,
            @Query("from") String from,
            @Query("to") String to,
            @Query("unit") String unit,
            @Query("routeType") String routeType
    );

    @GET("staticmap/v5/map")
    @Streaming
    Call<ResponseBody> downloadImage(
            @Query("key") String key,
            @Query("size") String size,
            @Query("session") String session,
            @Query("boundingBox") String boundingBox
    );
}
