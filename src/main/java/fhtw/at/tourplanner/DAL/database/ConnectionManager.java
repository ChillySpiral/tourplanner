package fhtw.at.tourplanner.DAL.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    //ToDo: Get them from Config
    private static String connectionString = "";
    private static String user = "";
    private static String password = "";
    private static Connection dbConnection;

    public static Connection getConnection(){
        if(dbConnection == null) {
            try{
                dbConnection = DriverManager.getConnection(connectionString, user, password);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return dbConnection;
    }
}