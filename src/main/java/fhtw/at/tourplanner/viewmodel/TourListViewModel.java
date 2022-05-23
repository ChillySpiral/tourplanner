package fhtw.at.tourplanner.viewmodel;

import fhtw.at.tourplanner.listener.TourSelectionListener;
import fhtw.at.tourplanner.model.TourModel;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class TourListViewModel {

    private ObservableList<TourModel> data = FXCollections.observableArrayList();
    private TourTabViewModel tourTabViewModel;
    private LogTableViewModel logTableViewModel;
    public List<TourSelectionListener> listeners = new ArrayList<>();

    public TourListViewModel(){
        addNewTour();
        addNewTour();
    }

    public ObservableList<TourModel> getData() {
        return data;
    }

    public ChangeListener<TourModel> getChangeListener() {
        return (observableValue, oldValue, newValue) -> notifyListeners(newValue);
    }

    public void addTourSelectionListener(TourSelectionListener listener){
        listeners.add(listener);
    }

    public void removeTourSelectionListener(TourSelectionListener listener){
        listeners.remove(listener);
    }

    private void notifyListeners(TourModel newValue) {
        for( var listener : listeners) {
            listener.changeSelection(newValue);
        }
    }

    public void setTours(List<TourModel> tourItems){
        //ToDo: Implement load from backend
    }

    public void addNewTour(){
        var newItem = new TourModel();
        newItem.setTitle("New Tour");
        data.add(newItem);
    }

    public void deleteTour(TourModel tourItem){
        data.remove(tourItem);
    }
}
