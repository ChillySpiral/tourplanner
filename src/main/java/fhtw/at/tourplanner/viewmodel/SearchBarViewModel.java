package fhtw.at.tourplanner.viewmodel;

import fhtw.at.tourplanner.BL.BLFactory;
import fhtw.at.tourplanner.BL.appManager.TourAppManager;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.listener.TourSearchListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class SearchBarViewModel {

    private final List<TourSearchListener> listeners = new ArrayList<>();
    private final TourAppManager tourAppManager = BLFactory.getTourAppManager();
    private StringProperty searchField = new SimpleStringProperty();
    public SearchBarViewModel(){

    }

    public void getToursList(){
        var tourIds = tourAppManager.searchTours(getSearchField());
        notifyListeners(tourIds);
    }
    public void addTourSearchListener(TourSearchListener listener) {
        listeners.add(listener);
    }
    public void removeTourSearchListener(TourSearchListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(List<TourModel> tourIds) {
        for (var listener : listeners) {
            listener.displaySearchResults(tourIds);
        }
    }

    public String getSearchField(){
        return searchField.get();
    }

    public StringProperty searchFieldProperty(){
        return searchField;
    }
}
