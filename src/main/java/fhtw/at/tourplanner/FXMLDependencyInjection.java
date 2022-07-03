package fhtw.at.tourplanner;

import fhtw.at.tourplanner.PL.view.ControllerFactory;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;

import java.io.IOException;

public class FXMLDependencyInjection {

    public static Parent load(String location) throws IOException {
        var loader = getLoader(location);
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
