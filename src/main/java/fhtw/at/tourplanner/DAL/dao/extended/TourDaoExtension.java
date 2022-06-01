package fhtw.at.tourplanner.DAL.dao.extended;

import fhtw.at.tourplanner.DAL.dao.Dao;
import fhtw.at.tourplanner.model.TourLog;
import fhtw.at.tourplanner.model.TourModel;

import java.util.List;

public interface TourDaoExtension extends Dao<TourModel> {
    List<TourLog> getLogsForTour(TourModel tour);
}
