package fhtw.at.tourplanner.DAL.mapQuestAPI;

import fhtw.at.tourplanner.DAL.model.fileSystem.Pair;
import fhtw.at.tourplanner.DAL.model.mapQuestModels.MapQuestModel;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.mapQuestModels.Route;

public interface MapQuestRepository {
    MapQuestModel getRouteInfo(TourModel tourModel);
    Pair<String, Route> getRouteImage(TourModel tourModel);
}
