package fhtw.at.tourplanner.BL;

import fhtw.at.tourplanner.DAL.DalFactory;
import fhtw.at.tourplanner.DAL.dao.Dao;
import fhtw.at.tourplanner.DAL.dao.extended.TourDaoExtension;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;

import java.util.List;

public class TourAppManagerImpl implements TourAppManager {

    private final TourDaoExtension tourModelDao;
    private final Dao<TourLog> tourLogDao;

    public TourAppManagerImpl(){
        tourModelDao = DalFactory.GetTourModelDao();
        tourLogDao = DalFactory.GetTourLogDao();
    }

    @Override
    public List<TourModel> getAllTours() {
        return tourModelDao.getAll();
    }

    @Override
    public TourModel createTour() {
        return tourModelDao.create(-1);
    }

    @Override
    public void deleteTour(TourModel tourModel) {
        tourModelDao.delete(tourModel);
    }

    @Override
    public void updateTour(TourModel tourModel) {
        tourModelDao.update(tourModel);
    }

    @Override
    public List<TourLog> getAllTourLogsForTour(TourModel tourModel) {
         return tourModelDao.getLogsForTour(tourModel);
    }

    @Override
    public List<TourLog> getAllTourLogs() {
        return tourLogDao.getAll();
    }

    @Override
    public TourLog createLog(int tourId) {
        return tourLogDao.create(tourId);
    }

    @Override
    public void deleteLog(TourLog log) {
        tourLogDao.delete(log);
    }

    @Override
    public void updateLog(TourLog log) {
        tourLogDao.update(log);
    }
}
