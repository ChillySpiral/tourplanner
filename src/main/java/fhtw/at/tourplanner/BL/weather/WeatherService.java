package fhtw.at.tourplanner.BL.weather;

import fhtw.at.tourplanner.DAL.model.weatherModel.WeatherModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("current.json")
    Call<WeatherModel> getWeatherInfo(
            @Query("key") String key,
            @Query("q") String query
    );

}
