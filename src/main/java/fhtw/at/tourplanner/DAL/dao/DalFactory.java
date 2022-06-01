package fhtw.at.tourplanner.DAL.dao;

import fhtw.at.tourplanner.DAL.database.Database;
import fhtw.at.tourplanner.DAL.dao.extended.TourDaoExtension;
import fhtw.at.tourplanner.DAL.dao.implementation.TourDao;
import fhtw.at.tourplanner.DAL.dao.implementation.TourDaoInMemory;
import fhtw.at.tourplanner.DAL.database.implementation.TourDatabase;
import fhtw.at.tourplanner.model.TourModel;

public class DalFactory {

    private static Dao<TourModel> tourDaoInMemory;
    private static TourDaoExtension tourDao;
    private static Database database;

    public static Dao<TourModel> GetTourModelDaoInMemory(){
        if(tourDaoInMemory == null){
            tourDaoInMemory = new TourDaoInMemory();
        }
        return tourDaoInMemory;
    }

    public static TourDaoExtension GetTourModelDao(){
        if(tourDao == null){
            tourDao = new TourDao();
        }
        return tourDao;
    }

    public static Database GetDatabase(){
        if(database == null){
            database = new TourDatabase();
        }
        return database;
    }
}
