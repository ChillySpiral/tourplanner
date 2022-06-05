package fhtw.at.tourplanner.DAL.mapQuestAPI;

import fhtw.at.tourplanner.DAL.model.mapQuestModels.MapQuestModel;
import fhtw.at.tourplanner.DAL.model.TourModel;

public interface MapQuestRepository {
    MapQuestModel getRouteInfo(TourModel tourModel);
    String getRouteImage(TourModel tourModel);
}
