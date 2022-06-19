package fhtw.at.tourplanner.view;

import fhtw.at.tourplanner.viewmodel.HomeViewModel;
import fhtw.at.tourplanner.viewmodel.TourTabViewModel;
import javafx.fxml.FXML;

public class HomeController {
    @FXML
    private SearchBarController searchBarController;
    @FXML
    private TourListController tourListController;
    @FXML
    private TourTabViewModel tourTabViewModel;

    private final HomeViewModel homeViewModel;

    public HomeController(HomeViewModel homeViewModel) {
        this.homeViewModel = homeViewModel;
    }

    @FXML
    public void initialize() {

    }
}
