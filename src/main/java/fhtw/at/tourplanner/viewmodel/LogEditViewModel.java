package fhtw.at.tourplanner.viewmodel;

import fhtw.at.tourplanner.DAL.model.enums.Difficulty;
import fhtw.at.tourplanner.DAL.model.enums.Rating;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalTime;

public class LogEditViewModel {

    private final StringProperty durationSeconds = new SimpleStringProperty();
    private final StringProperty durationMinutes = new SimpleStringProperty();
    private final StringProperty durationHours = new SimpleStringProperty();
    private LocalTime duration = LocalTime.now();
    private final StringProperty comment = new SimpleStringProperty();
    private final ObjectProperty<Difficulty> difficulty = new SimpleObjectProperty<>();
    private final ObjectProperty<Rating> rating = new SimpleObjectProperty<>();

    public LogEditViewModel(String duration, String comment, Difficulty difficulty, Rating rating){

        this.comment.setValue(comment);
        this.difficulty.setValue(difficulty);
        this.rating.setValue(rating);
    }

    public String getDurationSeconds() {
        return durationSeconds.toString();
    }
    public StringProperty durationSecondsProperty() {
        return durationSeconds;
    }

    public String getDurationMinutes() {
        return durationMinutes.toString();
    }
    public StringProperty durationMinutesProperty() {
        return durationMinutes;
    }

    public String getDurationHours() {
        return durationHours.toString();
    }
    public StringProperty durationHoursProperty() {
        return durationHours;
    }

    public String getComment() {
        return comment.get();
    }
    public StringProperty commentProperty() {
        return comment;
    }

    public Difficulty getDifficulty() {
        return difficulty.get();
    }
    public ObjectProperty<Difficulty> difficultyProperty() {
        return difficulty;
    }

    public Rating getRating() {
        return rating.get();
    }
    public ObjectProperty<Rating> ratingProperty() {
        return rating;
    }

    @Override
    public String toString(){
        return getDurationHours() + " " + getDurationMinutes() + " " + getComment() + " " + getDifficulty() + " " + getRating();
    }
}
