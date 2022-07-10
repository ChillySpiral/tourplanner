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
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires org.apache.logging.log4j;


    opens fhtw.at.tourplanner to javafx.fxml;
    exports fhtw.at.tourplanner;
    exports fhtw.at.tourplanner.PL.view;
    exports fhtw.at.tourplanner.DAL.mapQuestAPI;
    opens fhtw.at.tourplanner.PL.view to javafx.fxml;
    exports fhtw.at.tourplanner.DAL.mapQuestAPI.implementation;
    exports fhtw.at.tourplanner.DAL.model;
    exports fhtw.at.tourplanner.DAL.model.mapQuestModels;
    exports fhtw.at.tourplanner.PL.view.dialog;
    opens fhtw.at.tourplanner.PL.view.dialog to javafx.fxml;
    exports fhtw.at.tourplanner.DAL.model.export;
    exports fhtw.at.tourplanner.DAL.model.enums;
    exports fhtw.at.tourplanner.DAL.model.weatherModel;
}