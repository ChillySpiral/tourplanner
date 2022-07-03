package fhtw.at.tourplanner;

import fhtw.at.tourplanner.Configuration.AppConfigurationLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class TourPlanner extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        log.info("Tourplanner started.");

        AppConfigurationLoader.getInstance().getAppConfiguration();
        Parent root = FXMLDependencyInjection.load("homeView.fxml");
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Tourplanner");
        stage.setScene(scene);
        stage.setMinHeight(700);
        stage.setMinWidth(800);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}