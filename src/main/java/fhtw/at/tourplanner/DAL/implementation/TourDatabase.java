package fhtw.at.tourplanner.DAL.implementation;

import fhtw.at.tourplanner.DAL.database.ConnectionManager;
import fhtw.at.tourplanner.DAL.database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TourDatabase implements Database {
    private final Connection dbConnection;
    public TourDatabase(){
        dbConnection = ConnectionManager.getConnection();
    }

    @Override
    public List<HashMap<String, Object>> select(String query, List<Object> sqlParams) {
        if(query == null || query.isEmpty())
            return null;
        List<HashMap<String, Object>> result = null;

        try{
            PreparedStatement statement = dbConnection.prepareStatement(query);
            if(sqlParams != null){
                int i = 1;
                for(var param : sqlParams){
                    statement.setString(i, param.toString());
                    ++i;
                }
            }
            var resultSet = statement.executeQuery();
            result =  processQueryResult(resultSet);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int insert(String query, List<Object> sqlParams) {
        if (query == null || query.isEmpty())
            return -1;
        var expectedParamsCount = countParameters(query);
        List<HashMap<String, Object>> result = null;

        try {
            PreparedStatement statement = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            if (sqlParams != null) {
                int i = 1;
                for (var param : sqlParams) {
                    if(i-1 == expectedParamsCount)
                        break;
                    if(param != null){
                        statement.setString(i, param.toString());
                    } else {
                        statement.setString(i, null);
                    }
                    ++i;
                }
                var insertedElements = statement.executeUpdate();
                if(insertedElements > 0) {
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            return generatedKeys.getInt(1);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    @Override
    public boolean delete(String query, List<Object> sqlParams) {
        return false;
    }

    @Override
    public boolean update(String query, List<Object> sqlParams) {
        return false;
    }

    private List<HashMap<String, Object>> processQueryResult(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        List<HashMap<String, Object>> queryResult = new ArrayList<>();

        while(resultSet.next())
        {
            var entryResult = new HashMap<String, Object>();
            for(int i = 1; i <= columnsNumber; i++)
            {
                entryResult.put(rsmd.getColumnName(i), resultSet.getString(i));
            }
            if(!entryResult.isEmpty())
                queryResult.add(entryResult);
        }
        return queryResult;
    }

    private int countParameters(String str)
    {
        int count = 0;

        for(int i=0; i < str.length(); i++)
        {    if(str.charAt(i) == '?')
            count++;
        }

        return count;
    }
}
