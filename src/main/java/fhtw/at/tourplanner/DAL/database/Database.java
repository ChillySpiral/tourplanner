package fhtw.at.tourplanner.DAL.database;

import java.util.HashMap;
import java.util.List;

public interface Database {
    List<HashMap<String, Object>> select(String query, List<Object> sqlParams);
    boolean insert(String query, List<Object> sqlParams);
    boolean delete(String query, List<Object> sqlParams);
    boolean update(String query, List<Object> sqlParams);
}
