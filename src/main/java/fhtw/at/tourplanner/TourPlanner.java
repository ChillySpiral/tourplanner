package fhtw.at.tourplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TourPlanner extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLDependencyInjection.load("homeView.fxml");
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Tourplanner");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}