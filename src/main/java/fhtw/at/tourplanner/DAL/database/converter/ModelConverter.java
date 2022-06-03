package fhtw.at.tourplanner.DAL.database.converter;

import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;

public  class ModelConverter {

    public static TourModel convertToTourModel(HashMap<String, Object> params) {
        var id = Integer.parseInt(params.get("Id").toString());
        var title = (String) params.get("Title");
        var description = (String) params.get("Description");
        var from = (String) params.get("From");
        var to = (String) params.get("To");
        var transportType = (String) params.get("TransportType");
        var distance = params.get("Distance") == null ? 0 : Double.parseDouble(params.get("Distance").toString());
        LocalTime estimatedTime = params.get("EstimatedTime") == null ? LocalTime.of(0, 0, 0) : LocalTime.parse(params.get("EstimatedTime").toString());
        var imageFilename = (String) params.get("ImageFilename");

        return new TourModel(id, title, description, from, to, TransportTypeDBConverter.ConvertString(transportType), distance, estimatedTime, imageFilename);
    }

    public static TourLog convertToTourLogModel(HashMap<String, Object> params) {
        var id = Integer.parseInt(params.get("Id").toString());
        LocalDateTime dateTime = params.get("TotalTime") == null ? LocalDateTime.of(LocalDate.MIN, LocalTime.of(0, 0, 0)) : LocalDateTime.parse(params.get("EstimatedTime").toString());
        var comment = (String) params.get("Comment");
        var difficulty = (String) params.get("Difficulty");
        LocalTime totalTime = params.get("TotalTime") == null ? LocalTime.of(0, 0, 0) : LocalTime.parse(params.get("EstimatedTime").toString());
        var rating = (String) params.get("Rating");
        var tourId = Integer.parseInt(params.get("Id").toString());

        return new TourLog(id, dateTime, comment, difficulty, totalTime, rating, tourId);
    }
}
