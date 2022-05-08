package fhtw.at.tourplanner.viewmodel;

import fhtw.at.tourplanner.model.TourModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourListViewModel {

    private ObservableList<TourModel> data;

    public TourListViewModel(ObservableList data){
        this.data = data;
    }

    public ObservableList<TourModel> getData() {
        return data;
    }

    public void saveData(){
        data.add(new TourModel("New Tour", "Description"));
    }
}
