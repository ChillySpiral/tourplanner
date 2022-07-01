package fhtw.at.tourplanner.BL.pdfGenerator.helper;

import java.time.LocalTime;
import java.util.List;

public class Calculator {
    public static LocalTime calculateAverageTime(List<LocalTime> times){
        if(times == null || times.isEmpty())
            return null;
        long nanoSum = 0;

        for (var time : times) {
            if(time != null)
                nanoSum += time.toNanoOfDay();
        }
        return LocalTime.ofNanoOfDay(nanoSum / (times.size()));
    }
//ToDo: Difficulty and Rating AVG
    public static Double calculateAverageDistance(List<Double> distances){
        if(distances == null || distances.isEmpty())
            return null;
        Double distanceSum = 0.0;

        for (var distance : distances) {
            distanceSum += distance;
        }
        return (distanceSum / (distances.size()));
    }

}
