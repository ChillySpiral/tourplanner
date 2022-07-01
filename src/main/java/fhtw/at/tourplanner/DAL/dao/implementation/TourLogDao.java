package fhtw.at.tourplanner.DAL.dao.implementation;

import fhtw.at.tourplanner.DAL.dao.Dao;
import fhtw.at.tourplanner.DAL.database.Database;
import fhtw.at.tourplanner.DAL.database.converter.ModelConverter;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.enums.Difficulty;
import fhtw.at.tourplanner.DAL.model.enums.Rating;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TourLogDao implements Dao<TourLog> {

    private final Database database;
    public TourLogDao(Database database) {
        this.database = database;
    }

    @Override
    public Optional<TourLog> get(int id) {
        var queryString = "SELECT * FROM public.\"log\" WHERE Id = CAST(? AS INTEGER);";

        List<Object> params = new ArrayList<>();
        params.add(id);

        var queryResult = database.select(queryString, params);
        if (queryResult.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ModelConverter.convertToTourLogModel(queryResult.get(0)));
    }

    @Override
    public List<TourLog> getAll() {
        var queryString = "SELECT * FROM public.\"log\";";
        var queryResult = database.select(queryString, null);
        List<TourLog> result = new ArrayList<>();
        for (var log : queryResult) {
            result.add(ModelConverter.convertToTourLogModel(log));
        }
        return result;
    }

    @Override
    public TourLog create(int tourId) {
        var queryString = "INSERT INTO public.\"log\" (\"TourId\", \"DateTime\", \"Difficulty\", \"Rating\") VALUES(CAST(? AS INTEGER), ?, ?, ?);";
        var newItem = new TourLog();
        newItem.setDateTime(LocalDateTime.now());
        newItem.setDifficulty(Difficulty.Advanced);
        newItem.setRating(Rating.Neutral);
        List<Object> paramsId = new ArrayList<>();
        paramsId.add(tourId);
        paramsId.add(newItem.getDateTime());
        paramsId.add(newItem.getDifficulty().toString());
        paramsId.add(newItem.getRating().toString());

        var newId = database.insert(queryString, paramsId);

        if (newId == -1) {
            return null;
        }
        newItem.setLogId(newId);
        newItem.setTourId(tourId);
        return newItem;
    }

    @Override
    public void update(TourLog tourLog) {
        var queryString = "UPDATE public.\"log\" SET \"DateTime\" = ?, \"Comment\" = ?, \"Difficulty\" = ?, \"TotalTime\" = ?, \"Rating\" = ? WHERE \"Id\" = CAST(? AS INTEGER);";
        database.update(queryString, getParameters(tourLog));
    }

    @Override
    public void delete(TourLog tourLog) {
        var queryString = "DELETE FROM public.\"log\" WHERE \"Id\" = CAST(? AS INTEGER);";
        List<Object> paramsId = new ArrayList<>();
        paramsId.add(tourLog.getLogId());
        database.delete(queryString, paramsId);
    }

    private List<Object> getParameters(TourLog newItem) {
        List<Object> params = new ArrayList<>();
        params.add(newItem.getDateTime());
        params.add(newItem.getComment());
        params.add(newItem.getDifficulty());
        params.add(newItem.getTotalTime());
        params.add(newItem.getRating());
        params.add(newItem.getLogId());

        return params;
    }
}
