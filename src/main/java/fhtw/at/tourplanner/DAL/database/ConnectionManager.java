package fhtw.at.tourplanner.DAL.database;

import fhtw.at.tourplanner.Configuration.AppConfiguration;
import fhtw.at.tourplanner.Configuration.AppConfigurationLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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