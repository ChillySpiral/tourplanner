package fhtw.at.tourplanner.view;

import fhtw.at.tourplanner.DAL.model.enums.Difficulty;
import fhtw.at.tourplanner.DAL.model.enums.Rating;
import fhtw.at.tourplanner.viewmodel.LogEditViewModel;
import fhtw.at.tourplanner.viewmodel.TourEditViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class LogEditController {

    private final LogEditViewModel logEditViewModel;
    @FXML
    private ComboBox<Difficulty> difficulty;

    @FXML
    private ComboBox<Rating> rating;

    @FXML
    private TextArea comment;

    @FXML
    public void initialize() {
        /*
        title.textProperty().bindBidirectional(tourEditViewModel.titleProperty());
        description.textProperty().bindBidirectional(tourEditViewModel.descriptionProperty());
        from.textProperty().bindBidirectional(tourEditViewModel.fromProperty());
        to.textProperty().bindBidirectional(tourEditViewModel.toProperty());
        transportType.getItems().addAll(TransportType.Bicycle, TransportType.Car, TransportType.Foot);
        transportType.valueProperty().bindBidirectional(tourEditViewModel.transportTypeProperty()); */
        comment.textProperty().bindBidirectional(logEditViewModel.commentProperty());
        difficulty.getItems().addAll(Difficulty.Beginner, Difficulty.Intermediate, Difficulty.Advanced, Difficulty.Expert);
        difficulty.valueProperty().bindBidirectional(logEditViewModel.difficultyProperty());
        rating.getItems().addAll(Rating.Perfect, Rating.Good, Rating.Neutral, Rating.Bad, Rating.Terrible);
        rating.valueProperty().bindBidirectional(logEditViewModel.ratingProperty());

    }

    public LogEditController(LogEditViewModel logEditViewModel){

        this.logEditViewModel = logEditViewModel;
    }
}
