package fhtw.at.tourplanner.viewmodel;

import fhtw.at.tourplanner.BL.appManager.TourAppManager;
import fhtw.at.tourplanner.BL.BLFactory;
import fhtw.at.tourplanner.listener.TourSelectionListener;
import fhtw.at.tourplanner.DAL.model.TourModel;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class TourListViewModel {

    private final ObservableList<TourModel> data = FXCollections.observableArrayList();
    private final List<TourSelectionListener> listeners = new ArrayList<>();
    private final TourAppManager tourAppManager = BLFactory.getTourAppManager();

    public TourListViewModel() {
        var tours = tourAppManager.getAllTours();
        setTours(tours);
    }

    public ObservableList<TourModel> getData() {
        return data;
    }

    public ChangeListener<TourModel> getChangeListener() {
        return (observableValue, oldValue, newValue) -> notifyListeners(newValue);
    }

    public void addTourSelectionListener(TourSelectionListener listener) {
        listeners.add(listener);
    }

    public void removeTourSelectionListener(TourSelectionListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(TourModel newValue) {
        for (var listener : listeners) {
            listener.changeSelection(newValue);
        }
    }

    private void setTours(List<TourModel> tourItems) {
        data.clear();
        data.addAll(tourItems);
    }

    public TourModel addNewTour() {
        var newItem = tourAppManager.createTour();
        data.add(0, newItem);
        return newItem;
    }

    public void addImportTour(TourModel tourModel){
        data.add(tourModel);
    }

    public void deleteTour(TourModel tourItem) {
        tourAppManager.deleteTour(tourItem);
        data.remove(tourItem);
    }

    public void updateTour(TourModel tourItem){
        tourAppManager.updateTour(tourItem);
    }
}
