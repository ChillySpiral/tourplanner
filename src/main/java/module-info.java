module fhtw.at.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;


    opens fhtw.at.tourplanner to javafx.fxml;
    exports fhtw.at.tourplanner;
}