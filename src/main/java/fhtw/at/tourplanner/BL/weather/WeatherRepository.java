package fhtw.at.tourplanner.BL.weather;

import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.fileSystem.Pair;
import fhtw.at.tourplanner.DAL.model.mapQuestModels.MapQuestModel;
import fhtw.at.tourplanner.DAL.model.mapQuestModels.Route;
import fhtw.at.tourplanner.DAL.model.weatherModel.WeatherModel;

public interface WeatherRepository {
    WeatherModel getWeatherInfo(TourModel tourModel);
}
