module fhtw.at.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires retrofit2;
    requires retrofit2.converter.jackson;
    requires com.fasterxml.jackson.annotation;
    requires okhttp3;
    requires kernel;
    requires io;
    requires layout;


    opens fhtw.at.tourplanner to javafx.fxml;
    exports fhtw.at.tourplanner;
    exports fhtw.at.tourplanner.view;
    exports fhtw.at.tourplanner.DAL.mapQuestAPI;
    opens fhtw.at.tourplanner.view to javafx.fxml;
    exports fhtw.at.tourplanner.DAL.mapQuestAPI.implementation;
    exports fhtw.at.tourplanner.DAL.model;
    exports fhtw.at.tourplanner.DAL.model.mapQuestModels;
}