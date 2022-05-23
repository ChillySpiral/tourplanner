package fhtw.at.tourplanner.viewmodel;

import fhtw.at.tourplanner.model.TourModel;

public class HomeViewModel {
    private SearchBarViewModel searchBarViewModel;
    private TourListViewModel tourListViewModel;
    private TourTabViewModel tourTabViewModel;
    private LogTableViewModel logTableViewModel;

    public HomeViewModel(SearchBarViewModel searchBarViewModel, TourListViewModel tourListViewModel, TourTabViewModel tourTabViewModel, LogTableViewModel logTableViewModel) {
        this.searchBarViewModel = searchBarViewModel;
        this.tourListViewModel = tourListViewModel;
        this.tourTabViewModel = tourTabViewModel;
        this.logTableViewModel = logTableViewModel;

        this.tourListViewModel.addTourSelectionListener(selectedTourItem -> selectTour(selectedTourItem));
    }

    private void selectTour(TourModel selectedTourItem){
        this.tourTabViewModel.setTourModel(selectedTourItem);
    }
}
