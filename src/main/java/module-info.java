module fhtw.at.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;


    opens fhtw.at.tourplanner to javafx.fxml;
    exports fhtw.at.tourplanner;
    exports fhtw.at.tourplanner.view;
    opens fhtw.at.tourplanner.view to javafx.fxml;
}