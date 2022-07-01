package fhtw.at.tourplanner.view;

import fhtw.at.tourplanner.DAL.model.enums.Difficulty;
import fhtw.at.tourplanner.DAL.model.enums.Rating;
import fhtw.at.tourplanner.viewmodel.LogEditViewModel;
import fhtw.at.tourplanner.viewmodel.TourEditViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.DateTimeStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.UnaryOperator;

public class LogEditController {

    private final LogEditViewModel logEditViewModel;
    @FXML
    private ComboBox<Difficulty> difficulty;

    @FXML
    private ComboBox<Rating> rating;

    @FXML
    private TextArea comment;

    @FXML
    private TextField duration;

    @FXML
    public void initialize() throws ParseException {

        comment.textProperty().bindBidirectional(logEditViewModel.commentProperty());
        difficulty.getItems().addAll(Difficulty.Beginner, Difficulty.Intermediate, Difficulty.Advanced, Difficulty.Expert);
        difficulty.valueProperty().bindBidirectional(logEditViewModel.difficultyProperty());
        rating.getItems().addAll(Rating.Perfect, Rating.Good, Rating.Neutral, Rating.Bad, Rating.Terrible);
        rating.valueProperty().bindBidirectional(logEditViewModel.ratingProperty());
        duration.textProperty().bindBidirectional(logEditViewModel.durationProperty());

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        duration.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format), format.parse(duration.getText())));

    }

    public LogEditController(LogEditViewModel logEditViewModel){

        this.logEditViewModel = logEditViewModel;
    }
}
