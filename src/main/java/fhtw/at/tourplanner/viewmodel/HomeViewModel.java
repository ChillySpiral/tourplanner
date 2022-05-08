package fhtw.at.tourplanner.viewmodel;

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
    }
}
