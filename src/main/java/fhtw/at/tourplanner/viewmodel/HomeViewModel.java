package fhtw.at.tourplanner.viewmodel;

import fhtw.at.tourplanner.DAL.model.TourModel;

public class HomeViewModel {
    private SearchBarViewModel searchBarViewModel;
    private TourListViewModel tourListViewModel;
    private TourTabViewModel tourTabViewModel;

    public HomeViewModel(SearchBarViewModel searchBarViewModel, TourListViewModel tourListViewModel, TourTabViewModel tourTabViewModel) {
        this.searchBarViewModel = searchBarViewModel;
        this.tourListViewModel = tourListViewModel;
        this.tourTabViewModel = tourTabViewModel;

        registerListeners();
    }

    private void selectTour(TourModel selectedTourItem){
        this.tourTabViewModel.setTourModel(selectedTourItem);
    }

    /*
    * Info:
    * selectedTourItem -> selectTour(selectedTourItem) definiert einen Listener, der sich ein TourModel Object nimmt
    * und das an selectTour weitergibt
    *
    * Die Lambda Funktion ist die Implementation der changeSelection Methode aus dem Interface TourSelectionListener
    * */
    private void registerListeners(){
        this.tourListViewModel.addTourSelectionListener(selectedTourItem -> selectTour(selectedTourItem));
    }
}
