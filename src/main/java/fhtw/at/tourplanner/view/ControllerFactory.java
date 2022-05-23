package fhtw.at.tourplanner.view;

import fhtw.at.tourplanner.viewmodel.*;

public class ControllerFactory {
    private final HomeViewModel homeViewModel;
    private final LogTableViewModel logTableViewModel;
    private final SearchBarViewModel searchBarViewModel;
    private final TourListViewModel tourListViewModel;
    private final TourTabViewModel tourTabViewModel;

    //Singleton Instance of ControllerFactory
    private static ControllerFactory instance = new ControllerFactory();

    public ControllerFactory() {
        logTableViewModel = new LogTableViewModel();
        searchBarViewModel = new SearchBarViewModel();
        tourTabViewModel = new TourTabViewModel();
        tourListViewModel = new TourListViewModel();
        homeViewModel = new HomeViewModel(searchBarViewModel, tourListViewModel, tourTabViewModel, logTableViewModel);
    }

    public static ControllerFactory getInstance() {
        return instance;
    }

    //Factory Method Pattern
    public Object create(Class<?> controllerClass) {
        if (controllerClass == HomeController.class)
            return new HomeController(homeViewModel);
        if (controllerClass == LogTableController.class)
            return new LogTableController(logTableViewModel);
        if (controllerClass == SearchBarController.class)
            return new SearchBarController(searchBarViewModel);
        if (controllerClass == TourListController.class)
            return new TourListController(tourListViewModel);
        if (controllerClass == TourTabController.class)
            return new TourTabController(tourTabViewModel);

        throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
    }
}
