package fhtw.at.tourplanner.PL.view;

import fhtw.at.tourplanner.BL.BLFactory;
import fhtw.at.tourplanner.PL.viewmodel.HomeViewModel;
import fhtw.at.tourplanner.PL.viewmodel.SearchBarViewModel;
import fhtw.at.tourplanner.PL.viewmodel.TourListViewModel;
import fhtw.at.tourplanner.PL.viewmodel.TourTabViewModel;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ControllerFactory {
    private final HomeViewModel homeViewModel;
    private final SearchBarViewModel searchBarViewModel;
    private final TourListViewModel tourListViewModel;
    private final TourTabViewModel tourTabViewModel;

    //Singleton Instance of ControllerFactory
    private static ControllerFactory instance = new ControllerFactory();

    public ControllerFactory() {
        searchBarViewModel = new SearchBarViewModel();
        tourTabViewModel = new TourTabViewModel(BLFactory.getTourAppManager());
        tourListViewModel = new TourListViewModel();
        homeViewModel = new HomeViewModel(searchBarViewModel, tourListViewModel, tourTabViewModel);
    }

    public static ControllerFactory getInstance() {
        return instance;
    }

    //Factory Method Pattern
    public Object create(Class<?> controllerClass) {
        if (controllerClass == HomeController.class)
            return new HomeController(homeViewModel);
        if (controllerClass == SearchBarController.class)
            return new SearchBarController(searchBarViewModel);
        if (controllerClass == TourListController.class)
            return new TourListController(tourListViewModel);
        if (controllerClass == TourTabController.class)
            return new TourTabController(tourTabViewModel);


        log.fatal("ControllerFactory failed: Unknown controller class. [class: " + controllerClass + " ]");
        throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
    }
}
