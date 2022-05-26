package fhtw.at.tourplanner.view;

import fhtw.at.tourplanner.viewmodel.SearchBarViewModel;
import javafx.fxml.FXML;

public class SearchBarController {
    private final SearchBarViewModel searchBarViewModel;

    public SearchBarController(SearchBarViewModel searchBarViewModel) {
        this.searchBarViewModel = searchBarViewModel;
    }

    @FXML
    public void initialize() {

    }
}
