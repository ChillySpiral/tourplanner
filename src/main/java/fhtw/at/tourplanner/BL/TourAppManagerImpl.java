package fhtw.at.tourplanner.BL;

import fhtw.at.tourplanner.DAL.dao.DalFactory;
import fhtw.at.tourplanner.DAL.dao.Dao;
import fhtw.at.tourplanner.model.TourModel;

import java.util.List;

public class TourAppManagerImpl implements TourAppManager {

    private final Dao<TourModel> tourModelDao;

    public TourAppManagerImpl(){
        tourModelDao = DalFactory.GetTourModelDaoInMemory();
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
}
