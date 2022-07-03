package fhtw.at.tourplanner.DAL.database.implementation;

import fhtw.at.tourplanner.DAL.database.ConnectionManager;
import fhtw.at.tourplanner.DAL.database.Database;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Log4j2
public class TourDatabase implements Database {
    private final Connection dbConnection = ConnectionManager.getConnection();
    public TourDatabase(){
    }

    @Override
    public List<HashMap<String, Object>> select(String query, List<Object> sqlParams) {
        if(query == null || query.isEmpty())
            return null;
        List<HashMap<String, Object>> result = null;

        try{
            PreparedStatement statement = dbConnection.prepareStatement(query);
            setParameters(statement, query, sqlParams);

            var resultSet = statement.executeQuery();
            result =  processQueryResult(resultSet);
        } catch(SQLException e){
            log.warn("Select failed in TourDatabase. [ error: " + e + " ]"); // TODO: ok?
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int insert(String query, List<Object> sqlParams) {
        if (query == null || query.isEmpty())
            return -1;

        try {
            PreparedStatement statement = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            setParameters(statement, query, sqlParams);

            var insertedElements = statement.executeUpdate();

            if (insertedElements > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Insert failed in TourDatabase. [ error: " + e + " ]"); // TODO: ok?
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    @Override
    public boolean delete(String query, List<Object> sqlParams) {
        if (query == null || query.isEmpty()) {
            log.warn("Select failed in TourDatabase because query was false."); // TODO: ok?
            return false;
        }

        try {
            PreparedStatement statement = dbConnection.prepareStatement(query);
            setParameters(statement, query, sqlParams);

            var modifiedElements = statement.executeUpdate();

            if (modifiedElements == 1) {
                return true;
            }
        } catch (SQLException e) {
            log.warn("Delete failed in TourDatabase. [ error: " + e + " ]"); // TODO: ok?
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean update(String query, List<Object> sqlParams) {
        if (query == null || query.isEmpty()) {
            log.warn("Update failed in TourDatabase because query was false."); // TODO: ok?
            return false;
        }

        try {
            PreparedStatement statement = dbConnection.prepareStatement(query);
            setParameters(statement, query, sqlParams);

            var modifiedElements = statement.executeUpdate();

            if (modifiedElements == 1) {
                return true;
            }
        } catch (SQLException e) {
            log.warn("Update failed in TourDatabase. [ error: " + e + " ]"); // TODO: ok?
            e.printStackTrace();
            return false;
        }
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

    private int countParameters(String str) {
        int count = 0;

        for(int i=0; i < str.length(); i++)
        {    if(str.charAt(i) == '?')
            count++;
        }

        return count;
    }

    private void setParameters(PreparedStatement statement,String query ,List<Object> sqlParams) throws SQLException {
        var expectedParamsCount = countParameters(query);
        if (sqlParams != null) {
            int pos = 1;
            for (var param : sqlParams) {
                if (pos - 1 == expectedParamsCount)
                    break;
                if (param != null) {
                    statement.setString(pos, param.toString());
                } else {
                    statement.setString(pos, null);
                }
                ++pos;
            }
        }
    }

}
