package fhtw.at.tourplanner.DAL;

import fhtw.at.tourplanner.Configuration.AppConfigurationLoader;
import fhtw.at.tourplanner.DAL.FileSystem.FileSystem;
import fhtw.at.tourplanner.DAL.FileSystem.implementation.FileSystemImpl;
import fhtw.at.tourplanner.DAL.dao.Dao;
import fhtw.at.tourplanner.DAL.dao.extended.TourDaoExtension;
import fhtw.at.tourplanner.DAL.dao.implementation.TourDao;
import fhtw.at.tourplanner.DAL.dao.implementation.TourLogDao;
import fhtw.at.tourplanner.DAL.database.Database;
import fhtw.at.tourplanner.DAL.database.implementation.TourDatabase;
import fhtw.at.tourplanner.DAL.mapQuestAPI.MapQuestRepository;
import fhtw.at.tourplanner.DAL.mapQuestAPI.implementation.MapQuestRepositoryImpl;
import fhtw.at.tourplanner.DAL.model.TourLog;

public class DalFactory {
    private static TourDaoExtension tourDao;
    private static Dao<TourLog> tourLogDao;
    private static Database database;
    private static FileSystem fileSystem;
    private static MapQuestRepository mapQuestRepository;

    public static TourDaoExtension GetTourModelDao(){
        if(tourDao == null){
            tourDao = new TourDao(GetDatabase(), GetFileSystem());
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
            fileSystem = new FileSystemImpl(AppConfigurationLoader.getInstance().getAppConfiguration());
        }
        return fileSystem;
    }

    public static MapQuestRepository GetMapQuestRepository(){
        if(mapQuestRepository == null){
            mapQuestRepository = new MapQuestRepositoryImpl(AppConfigurationLoader.getInstance().getAppConfiguration(), GetFileSystem());
        }
        return mapQuestRepository;
    }
}
