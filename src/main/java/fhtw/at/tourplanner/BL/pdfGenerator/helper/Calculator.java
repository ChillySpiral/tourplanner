package fhtw.at.tourplanner.BL.pdfGenerator.helper;

import fhtw.at.tourplanner.DAL.model.enums.Difficulty;
import fhtw.at.tourplanner.DAL.model.enums.Rating;

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

    public static Difficulty calculateAverageDifficulty(List<Difficulty> difficulties){
        if(difficulties == null || difficulties.isEmpty())
            return null;
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

        Double calculatedAverageDifficulty = Double.valueOf((difficultySum / (difficulties.size())));
        var result = Math.round(calculatedAverageDifficulty);

        if(result == 0) return Difficulty.Beginner;
        if(result == 1) return Difficulty.Intermediate;
        if(result == 2) return Difficulty.Advanced;
        else return Difficulty.Expert;

    }

    public static Rating calculateAverageRating(List<Rating> ratings){
        if(ratings == null || ratings.isEmpty())
            return null;
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

}
