package fhtw.at.tourplanner.PL.view;

import fhtw.at.tourplanner.PL.viewmodel.SearchBarViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SearchBarController {
    private final SearchBarViewModel searchBarViewModel;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private Button clearButton;

    public SearchBarController(SearchBarViewModel searchBarViewModel) {
        this.searchBarViewModel = searchBarViewModel;
    }

    @FXML
    public void initialize() {
        searchField.textProperty().bindBidirectional(searchBarViewModel.searchFieldProperty());
        clearButton.disableProperty().bind(searchBarViewModel.searchFieldProperty().isEmpty());
    }

    public void searchTours(ActionEvent actionEvent) {
        searchBarViewModel.getToursList();
    }

    public void clearSearch(ActionEvent actionEvent) {
        searchField.textProperty().setValue("");
        searchTours(new ActionEvent());
    }
}
