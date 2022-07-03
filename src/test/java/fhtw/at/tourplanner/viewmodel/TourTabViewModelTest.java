package fhtw.at.tourplanner.viewmodel;

import fhtw.at.tourplanner.BL.appManager.TourAppManager;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.enums.Difficulty;
import fhtw.at.tourplanner.DAL.model.enums.Rating;
import fhtw.at.tourplanner.DAL.model.enums.TransportType;
import fhtw.at.tourplanner.PL.viewmodel.TourTabViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TourTabViewModelTest {

    @Mock
    TourAppManager tourAppManager;

    private TourTabViewModel tourTabViewModel;

    private List<TourModel> tourModels;

    private List<TourLog> tourLogs;

    @BeforeEach
    private void initTourModelsList(){
        tourModels = new ArrayList<>();
        tourModels.add(new TourModel(1, "Title", "This is a test tour", "Vienna", "Graz", TransportType.Car, 180.0, LocalTime.of(2,30,1), null));
        tourModels.add(new TourModel(2, "Title2", "Going on holiday", "Vienna", "Milan", TransportType.Car, 846.0, LocalTime.of(8,45,6), null));
        tourModels.add(new TourModel(3, "Title3", "Small walk", "Vienna", "Raasdorf", TransportType.Foot, 8.0, LocalTime.of(2,12,11), "Test.jpeg"));

    }

    @BeforeEach
    private void initTourLogsList(){
        tourLogs = new ArrayList<>();
        tourLogs.add(new TourLog(1, LocalDateTime.of(2022,7,3,17,5), "Nice Tour", Difficulty.Advanced, LocalTime.of(8,2,2), Rating.Bad, 1));
        tourLogs.add(new TourLog(2, LocalDateTime.of(2022,3,23,7,5), "Long and boring", Difficulty.Beginner, LocalTime.of(7,22,32), Rating.Good, 3));
        tourLogs.add(new TourLog(3, LocalDateTime.of(2021,3,4,14,2), "Quick and easy", Difficulty.Expert, LocalTime.of(3,27,29), Rating.Neutral, 2));

    }

    @BeforeEach
    void initTourTabViewModel(){
        tourTabViewModel = new TourTabViewModel(tourAppManager);
    }

    @Test
    void calculatePopularity() {
        var tour = tourModels.get(0);
        Mockito.when(tourAppManager.getAllTourLogs()).thenReturn(tourLogs);
        Mockito.when(tourAppManager.getAllTourLogsForTour(tour)).thenReturn(List.of(tourLogs.get(0)));
        tourTabViewModel.setTourModel(tour);

        tourTabViewModel.calculatePopularity();
        var actual = tourTabViewModel.popularityProperty().get();
        assertThat(actual).isNotNull();
        assertThat(actual).isNotEmpty();
        assertThat(actual).isEqualTo("Very popular! Over 33.33 % of all tour logs are for this tour.");
    }

    @Test
    void calculateChildfriendliness() {
        var tour = tourModels.get(0);
        Mockito.when(tourAppManager.getAllTourLogsForTour(tour)).thenReturn(List.of(tourLogs.get(0)));
        tourTabViewModel.setTourModel(tour);

        tourTabViewModel.calculateChildfriendliness();
        var actual = tourTabViewModel.childfriendlinessProperty().get();
        assertThat(actual).isNotNull();
        assertThat(actual).isNotEmpty();
        assertThat(actual).isEqualTo("For advanced or older children.");

    }
}