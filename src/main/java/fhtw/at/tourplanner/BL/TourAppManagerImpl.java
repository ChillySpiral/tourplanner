package fhtw.at.tourplanner.BL;

import fhtw.at.tourplanner.DAL.DalFactory;
import fhtw.at.tourplanner.DAL.dao.Dao;
import fhtw.at.tourplanner.DAL.model.TourModel;

import java.util.List;

public class TourAppManagerImpl implements TourAppManager {

    private final Dao<TourModel> tourModelDao;

    public TourAppManagerImpl(){
        tourModelDao = DalFactory.GetTourModelDao();
    }

    @Override
    public List<TourModel> getAllTours() {
        return tourModelDao.getAll();
    }

    @Override
    public TourModel createTour() {
        return tourModelDao.create();
    }

    @Override
    public void deleteTour(TourModel tourModel) {
        tourModelDao.delete(tourModel);
    }

    @Override
    public void updateTour(TourModel tourModel) {
        tourModelDao.update(tourModel);
    }
}
