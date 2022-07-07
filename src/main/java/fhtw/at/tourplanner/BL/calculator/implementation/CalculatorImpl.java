package fhtw.at.tourplanner.BL.calculator.implementation;

import fhtw.at.tourplanner.BL.calculator.Calculator;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.enums.Difficulty;
import fhtw.at.tourplanner.DAL.model.enums.Rating;
import fhtw.at.tourplanner.DAL.model.enums.TransportType;
import lombok.extern.log4j.Log4j2;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class CalculatorImpl implements Calculator {
    public LocalTime calculateAverageTime(List<LocalTime> times){
        if(times == null || times.isEmpty()) {
            log.info("Calculate average time failed because the list of values was null or empty.");
            return null;
        }
        long nanoSum = 0;

        for (var time : times) {
            if(time != null)
                nanoSum += time.toNanoOfDay();
        }
        return LocalTime.ofNanoOfDay(nanoSum / (times.size()));
    }

    public Difficulty calculateAverageDifficulty(List<Difficulty> difficulties){
        if(difficulties == null || difficulties.isEmpty()) {
            log.info("Calculate average difficulty failed because the list of values was null or empty.");
            return null;
        }
        int difficultySum = 0;

        for (var difficulty : difficulties) {
            switch (difficulty){
                case Beginner -> {
                    difficultySum += 0;
                }
                case Intermediate -> {
                    difficultySum += 1;
                }
                case Advanced -> {
                    difficultySum += 2;
                }
                case Expert -> {
                    difficultySum += 3;
                }
            }
        }

        var calculatedAverageDifficulty = (double) difficultySum / difficulties.size();
        var result = Math.round(calculatedAverageDifficulty);

        if(result == 0) return Difficulty.Beginner;
        if(result == 1) return Difficulty.Intermediate;
        if(result == 2) return Difficulty.Advanced;
        else return Difficulty.Expert;

    }

    public Rating calculateAverageRating(List<Rating> ratings){
        if(ratings == null || ratings.isEmpty()) {
            log.info("Calculate average rating failed because the list of values was null or empty.");
            return null;
        }
        int ratingSum = 0;

        for (var rating : ratings) {
            switch (rating){
                case Terrible -> {
                    ratingSum += 0;
                }
                case Bad -> {
                    ratingSum += 1;
                }
                case Neutral -> {
                    ratingSum += 2;
                }
                case Good -> {
                    ratingSum += 3;
                }
                case Perfect -> {
                    ratingSum += 4;
                }
            }
        }

        Double calculatedAverageRating = Double.valueOf((ratingSum / (ratings.size())));
        var result = Math.round(calculatedAverageRating);

        if(result == 0) return Rating.Terrible;
        if(result == 1) return Rating.Bad;
        if(result == 2) return Rating.Neutral;
        if(result == 3) return Rating.Good;
        else return Rating.Perfect;

    }

    public String calculatePopularity(int allLogsSize, int tourLogSize) {
        double percentage;

        if(allLogsSize == 0) {
            log.warn("Could not calculate popularity because there are no logs in existence.");
            percentage = 0;
        }
        else
            percentage = (double) tourLogSize/allLogsSize;

        if(0 == percentage)
            return "Not popular.";
        else if(0.5 < percentage)
            return "The most popular! Over 50 % of all tour logs are for this tour.";
        else if(0.5 == percentage)
            return "Very popular! 50 % of tour all logs are for this tour.";
        else if(0.3333 <= percentage)
            return "Very popular! Over 33.33 % of all tour logs are for this tour.";
        else if(0.25 <= percentage)
            return "Very popular! Over 25 % of all tour logs are for this tour.";
        else if(0.2 <= percentage)
            return "Fairly popular! Over 20 % of all tour logs are for this tour.";
        else if(0.1 <= percentage)
            return "Somewhat popular. Over 10 % of all tour logs are for this tour.";
        else if(0.05 <= percentage)
            return "Slightly popular. Over 5 % of all tour logs are for this tour.";
        else
        return "Not very popular. Less than 5 % of all tour logs are for this tour.";

    }

    public String calculateChildFriendliness(TourModel data, List<TourLog> tourLogs) {
        if(data == null){
            log.warn("No tour data available. Data is null");
            return "Not enough data.";
        }

        if(null == tourLogs || tourLogs.isEmpty()) {
            log.warn("Could not calculate child-friendliness because there exist no logs for this tour. [ tourId: " + data.getTourId() + " ]");
            return "Not enough data.";
        }

        Difficulty averageDifficulty = this.calculateAverageDifficulty(tourLogs.stream().map(TourLog::getDifficulty).collect(Collectors.toList()));
        LocalTime averageDuration = this.calculateAverageTime(tourLogs.stream().map(TourLog::getTotalTime).collect(Collectors.toList()));

        if(null == averageDifficulty || null == averageDuration) {
            log.warn("Could not calculate child-friendliness because average difficulty or average duration did not return any values. [ tourId: " + data.getTourId() + " ]");
            return "Not enough data.";
        }

        double distance = data.getTourDistance();
        int durationMinutes = averageDuration.getMinute()+averageDuration.getHour()*60;
        TransportType transport = data.getTransportType();

        int friendlinessLevel = 6;

        if(TransportType.Car == transport) {
            if(350 < distance)
                friendlinessLevel--;
            if(600 < distance)
                friendlinessLevel--;

            if(Difficulty.Advanced == averageDifficulty)
                friendlinessLevel--;
            else if(Difficulty.Expert == averageDifficulty)
                friendlinessLevel-=2;

            if(5*60 < durationMinutes)
                friendlinessLevel--;
            if(8*60 < durationMinutes)
                friendlinessLevel--;
        }
        else if(TransportType.Bicycle == transport) {
            //
            if(30 < distance)
                friendlinessLevel--;
            if(60 < distance)
                friendlinessLevel--;

            if(Difficulty.Advanced == averageDifficulty)
                friendlinessLevel--;
            else if(Difficulty.Expert == averageDifficulty)
                friendlinessLevel-=2;

            if(3*60 < durationMinutes)
                friendlinessLevel--;
            if(6*60 < durationMinutes)
                friendlinessLevel--;
        }
        else { // Foot
            // max 10km
            if(10 < distance)
                friendlinessLevel--;
            if(20 < distance)
                friendlinessLevel--;

            if(Difficulty.Advanced == averageDifficulty)
                friendlinessLevel--;
            else if(Difficulty.Expert == averageDifficulty)
                friendlinessLevel-=2;

            if(4*60 < durationMinutes)
                friendlinessLevel--;
            if(8*60 < durationMinutes)
                friendlinessLevel--;
        }

        switch(friendlinessLevel){
            case 6:
            case 5: return "Child friendly.";
            case 4:
            case 3: return "For advanced or older children.";
            case 2:
            case 1:
            case 0: return "Not suitable for children.";

        }
    return "Not enough data";
    }

}
