package fhtw.at.tourplanner.DAL.database;

import fhtw.at.tourplanner.Configuration.AppConfiguration;
import fhtw.at.tourplanner.Configuration.AppConfigurationLoader;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Log4j2
public class ConnectionManager {
    private static Connection dbConnection;
    private static AppConfiguration appConfiguration;
    private ConnectionManager(){
    }
    public static Connection getConnection(){
        if(dbConnection == null) {
            try{
                dbConnection = DriverManager.getConnection(getAppConfiguration().getDatasourceUrl(), getAppConfiguration().getDatasourceUsername(), getAppConfiguration().getDatasourcePassword());
            } catch(SQLException e){
                log.fatal("Connection to the database could not be established [ error: " + e.getMessage() + " ]");
                e.printStackTrace();
            }
        }
        return dbConnection;
    }

    private static AppConfiguration getAppConfiguration(){
        if(appConfiguration == null){
            appConfiguration = AppConfigurationLoader.getInstance().getAppConfiguration();
        }
        return appConfiguration;
    }
}