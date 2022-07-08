package fhtw.at.tourplanner.BL.weather;

import fhtw.at.tourplanner.DAL.model.mapQuestModels.MapQuestModel;
import fhtw.at.tourplanner.DAL.model.weatherModel.WeatherModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface WeatherService {

    @GET("current.json")
    Call<WeatherModel> getWeatherInfo(
            @Query("key") String key,
            @Query("q") String query
    );

}
