package fhtw.at.tourplanner.DAL;

import fhtw.at.tourplanner.DAL.FileSystem.FileSystem;
import fhtw.at.tourplanner.DAL.mapQuestAPI.MapQuestRepository;
import fhtw.at.tourplanner.DAL.mapQuestAPI.implementation.MapQuestRepositoryImpl;
import fhtw.at.tourplanner.DAL.FileSystem.implementation.FileSystemTourImage;
import fhtw.at.tourplanner.DAL.dao.Dao;
import fhtw.at.tourplanner.DAL.dao.implementation.TourLogDao;
import fhtw.at.tourplanner.DAL.database.Database;
import fhtw.at.tourplanner.DAL.dao.extended.TourDaoExtension;
import fhtw.at.tourplanner.DAL.dao.implementation.TourDao;
import fhtw.at.tourplanner.DAL.dao.implementation.TourDaoInMemory;
import fhtw.at.tourplanner.DAL.database.implementation.TourDatabase;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;

public class DalFactory {

    private static Dao<TourModel> tourDaoInMemory;
    private static TourDaoExtension tourDao;
    private static Dao<TourLog> tourLogDao;
    private static Database database;
    private static FileSystem fileSystem;

    private static MapQuestRepository mapQuestRepository;

    public static Dao<TourModel> GetTourModelDaoInMemory(){
        if(tourDaoInMemory == null){
            tourDaoInMemory = new TourDaoInMemory();
        }
        return tourDaoInMemory;
    }

    public static TourDaoExtension GetTourModelDao(){
        if(tourDao == null){
            tourDao = new TourDao(GetDatabase(), GetMapQuestRepository());
        }
        return tourDao;
    }

    public static Dao<TourLog> GetTourLogDao(){
        if(tourLogDao == null){
            tourLogDao = new TourLogDao(GetDatabase());
        }
        return tourLogDao;
    }

    public static Database GetDatabase(){
        if(database == null){
            database = new TourDatabase();
        }
        return database;
    }

    public static FileSystem GetFileSystem(){
        if(fileSystem == null){
            fileSystem = new FileSystemTourImage();
        }
        return fileSystem;
    }

    public static MapQuestRepository GetMapQuestRepository(){
        if(mapQuestRepository == null){
            mapQuestRepository = new MapQuestRepositoryImpl();
        }
        return mapQuestRepository;
    }
}
