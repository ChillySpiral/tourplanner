package fhtw.at.tourplanner;

import fhtw.at.tourplanner.view.ControllerFactory;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class FXMLDependencyInjection {

    public static Parent load(String location) throws IOException {
        FXMLLoader loader = getLoader(location);
        return loader.load();
    }

    public static FXMLLoader getLoader(String location) {
        var loader = new FXMLLoader();
        loader.setLocation(FXMLDependencyInjection.class.getResource("/fhtw/at/tourplanner/" + location));
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setControllerFactory(controllerClass -> ControllerFactory.getInstance().create(controllerClass));

        return loader;
    }
}
