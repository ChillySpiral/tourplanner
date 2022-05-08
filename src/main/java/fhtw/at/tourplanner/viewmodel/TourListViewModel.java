package fhtw.at.tourplanner.viewmodel;

import fhtw.at.tourplanner.model.TourModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourListViewModel {

    private ObservableList<TourModel> data;

    public TourListViewModel(){
        this.data = FXCollections.observableArrayList(
                new TourModel("Radtour Donauinsel", "Von einem Ende zum anderen."),
                new TourModel("Wienerberg Wanderung", "Mit Mittagspause bei einem Restaurant.")
        );
    }

    public ObservableList<TourModel> getData() {
        return data;
    }

    public void saveData(){
        data.add(new TourModel("New Tour", "Description"));
    }
}
