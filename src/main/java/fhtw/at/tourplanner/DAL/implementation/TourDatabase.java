package fhtw.at.tourplanner.DAL.implementation;

import fhtw.at.tourplanner.DAL.database.ConnectionManager;
import fhtw.at.tourplanner.DAL.database.Database;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

public class TourDatabase implements Database {
    private final Connection dbConnection;
    public TourDatabase(){
        dbConnection = ConnectionManager.getConnection();
    }

    @Override
    public List<HashMap<String, Object>> select(String query, List<Object> sqlParams) {
        return null;
    }

    @Override
    public boolean insert(String query, List<Object> sqlParams) {
        return false;
    }

    @Override
    public boolean delete(String query, List<Object> sqlParams) {
        return false;
    }

    @Override
    public boolean update(String query, List<Object> sqlParams) {
        return false;
    }
}
