package fhtw.at.tourplanner.DAL.dao;

import fhtw.at.tourplanner.DAL.implementation.TourDaoInMemory;
import fhtw.at.tourplanner.model.TourModel;

public class DalFactory {

    private static Dao<TourModel> tourDao;
    private static Dao<TourModel> tourDaoInMemory;

    public static Dao<TourModel> GetTourModelDao(){
        throw new UnsupportedOperationException();
    }

    public static Dao<TourModel> GetTourModelDaoInMemory(){
        if(tourDaoInMemory == null){
            tourDaoInMemory = new TourDaoInMemory();
        }
        return tourDaoInMemory;
    }
}
