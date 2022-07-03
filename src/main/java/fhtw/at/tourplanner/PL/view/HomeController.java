package fhtw.at.tourplanner.PL.view;

import fhtw.at.tourplanner.PL.viewmodel.HomeViewModel;
import fhtw.at.tourplanner.PL.viewmodel.TourTabViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;

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

    public void exportTour(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Tour");
        fileChooser.setInitialFileName("exportTour_");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);

        File file = fileChooser.showSaveDialog(owner);

        if (file != null) {
            // TODO: add logs here? with if its null?
            homeViewModel.exportTour(file);
        }
    }

    public void importTour(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Tour Import");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);

        File file = fileChooser.showOpenDialog(owner);

        if(file != null){
            // TODO: add logs here? with if its null?
            homeViewModel.importTour(file);
        }
    }

    public void tourReport(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Tour Report");
        fileChooser.setInitialFileName("tourReport_");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);

        File file = fileChooser.showSaveDialog(owner);

        if (file != null) {
            // TODO: add logs here? with if its null?
            homeViewModel.generateReportPdf(file);
        }
    }

    public void summaryReport(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Summary Report");
        fileChooser.setInitialFileName("summaryReport_");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);

        File file = fileChooser.showSaveDialog(owner);

        if (file != null) {
            // TODO: add logs here? with if its null?
            homeViewModel.generateSummaryPdf(file);
        }
    }
}
