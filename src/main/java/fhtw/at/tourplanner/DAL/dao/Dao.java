package fhtw.at.tourplanner.DAL.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(int id);

    List<T> getAll();

    T create();

    void update(T t);

    void delete(T t);
}
