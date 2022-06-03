package fhtw.at.tourplanner.DAL.mapQuestAPI;

import fhtw.at.tourplanner.DAL.model.MapQuestModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapQuestService {

    @GET("directions/v2/route")
    Call<MapQuestModel> getRouteInfo(
            @Query("key") String key,
            @Query("from") String from,
            @Query("to") String to,
            @Query("unit") String unit,
            @Query("routeType") String routeType
    );
}
