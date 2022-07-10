package fhtw.at.tourplanner.PL.viewmodel;

import fhtw.at.tourplanner.BL.appManager.TourAppManager;
import fhtw.at.tourplanner.BL.BLFactory;
import fhtw.at.tourplanner.DAL.model.TourModel;

import java.io.File;
import java.util.List;

public class HomeViewModel {
    private SearchBarViewModel searchBarViewModel;
    private TourListViewModel tourListViewModel;
    public TourTabViewModel tourTabViewModel;

    private final TourAppManager tourAppManager = BLFactory.getTourAppManager();

    public HomeViewModel(SearchBarViewModel searchBarViewModel, TourListViewModel tourListViewModel, TourTabViewModel tourTabViewModel) {
        this.searchBarViewModel = searchBarViewModel;
        this.tourListViewModel = tourListViewModel;
        this.tourTabViewModel = tourTabViewModel;

        registerListeners();
    }

    private void selectTour(TourModel selectedTourItem){
        this.tourTabViewModel.setTourModel(selectedTourItem);
    }
    private void filterTours(List<TourModel> tours){
        this.tourListViewModel.setTours(tours);
    }

    private void registerListeners(){
        this.tourListViewModel.addTourSelectionListener(selectedTourItem -> selectTour(selectedTourItem));
        this.searchBarViewModel.addTourSearchListener(tourIds -> filterTours(tourIds));
    }

    public void exportTour(File tourFile){
        if(tourTabViewModel != null && tourTabViewModel.getData() != null)
            tourAppManager.exportTour(tourFile, tourTabViewModel.getData());
    }

    public void importTour(File tourFile){
        var importedTour = tourAppManager.importTour(tourFile);
        if(importedTour != null)
            tourListViewModel.addImportTour(importedTour);
    }

    public void generateReportPdf(File pdfFile){
        if(tourTabViewModel != null && tourTabViewModel.getData() != null)
            tourAppManager.generateTourReport(tourTabViewModel.getData(), pdfFile);
    }

    public void generateSummaryPdf(File pdfFile){
        tourAppManager.generateSummaryReport(pdfFile);
    }
}
