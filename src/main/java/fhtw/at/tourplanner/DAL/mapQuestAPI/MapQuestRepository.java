package fhtw.at.tourplanner.DAL.mapQuestAPI;

import fhtw.at.tourplanner.DAL.model.MapQuestModel;
import fhtw.at.tourplanner.DAL.model.TourModel;

public interface MapQuestRepository {
    MapQuestModel getRouteInfo(TourModel tourModel);
}
