package fhtw.at.tourplanner.viewmodel;

import fhtw.at.tourplanner.DAL.model.enums.Difficulty;
import fhtw.at.tourplanner.DAL.model.enums.Rating;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LogEditViewModel {

    private final StringProperty duration = new SimpleStringProperty();
    private final StringProperty comment = new SimpleStringProperty();
    private final ObjectProperty<Difficulty> difficulty = new SimpleObjectProperty<>();
    private final ObjectProperty<Rating> rating = new SimpleObjectProperty<>();

    public LogEditViewModel(String duration, String comment, Difficulty difficulty, Rating rating){
        if(null != duration){
            this.duration.setValue(duration);
        }
        else {
            this.duration.setValue("00:00:00");
        }

        this.comment.setValue(comment);
        this.difficulty.setValue(difficulty);
        this.rating.setValue(rating);
    }

    public String getDuration() {
        return duration.get();
    }
    public StringProperty durationProperty() {
        return duration;
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
        return getDuration() + " " + getComment() + " " + getDifficulty() + " " + getRating();
    }
}
