package fhtw.at.tourplanner.viewmodel;

import fhtw.at.tourplanner.model.TourModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourListViewModel {

    private ObservableList<TourModel> data;
    private TourTabViewModel tourTabViewModel;
    private LogTableViewModel logTableViewModel;

    public TourListViewModel(TourTabViewModel tourTabViewModel, LogTableViewModel logTableViewModel){
        this.tourTabViewModel = tourTabViewModel;
        this.logTableViewModel = logTableViewModel;
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

    public void setSelectedItem(TourModel selectedItem){
        tourTabViewModel.setTourModel(selectedItem);
    }
}
