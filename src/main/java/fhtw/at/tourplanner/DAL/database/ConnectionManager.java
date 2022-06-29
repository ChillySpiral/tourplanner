package fhtw.at.tourplanner.DAL.database;

import fhtw.at.tourplanner.DAL.helper.ConfigurationLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static Connection dbConnection;
    private ConnectionManager(){

    }
    public static Connection getConnection(){
        if(dbConnection == null) {
            try{
                dbConnection = DriverManager.getConnection(ConfigurationLoader.getConfig("DBConnectionString"), ConfigurationLoader.getConfig("DBUser"), ConfigurationLoader.getConfig("DBPw"));
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return dbConnection;
    }
}