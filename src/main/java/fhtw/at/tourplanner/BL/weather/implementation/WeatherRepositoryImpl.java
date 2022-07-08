package fhtw.at.tourplanner.BL.weather.implementation;

import fhtw.at.tourplanner.BL.weather.WeatherRepository;
import fhtw.at.tourplanner.BL.weather.WeatherService;
import fhtw.at.tourplanner.Configuration.AppConfiguration;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.weatherModel.WeatherModel;
import lombok.extern.log4j.Log4j2;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

@Log4j2
public class WeatherRepositoryImpl implements WeatherRepository {

    private final WeatherService service;
    private final String weatherKey;

    public WeatherRepositoryImpl(AppConfiguration appConfiguration){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.weatherapi.com/v1/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        service = retrofit.create(WeatherService.class);
        weatherKey = appConfiguration.getWeatherKey();
    }

    @Override
    public WeatherModel getWeatherInfo(TourModel tourModel) {
        if(tourModel.getTo() == null  || tourModel.getTo().isEmpty())
            return null;

        final WeatherModel result;
        try{
            log.info("Retrieving weather from WeatherApi for [name: "+tourModel.getTo()+"]");
            result = service.getWeatherInfo(weatherKey, tourModel.getTo()).execute().body();
            return result;
        } catch (IOException e) {
            log.error("Get Weather for tour [id: "+tourModel.getTourId()+"] failed [error: "+e.getMessage()+"]");
        }
        return null;
    }


}
