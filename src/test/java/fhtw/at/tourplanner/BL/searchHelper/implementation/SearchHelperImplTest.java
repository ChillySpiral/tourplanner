package fhtw.at.tourplanner.BL.searchHelper.implementation;

import fhtw.at.tourplanner.BL.searchHelper.SearchHelper;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.enums.Difficulty;
import fhtw.at.tourplanner.DAL.model.enums.Rating;
import fhtw.at.tourplanner.DAL.model.enums.TransportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SearchHelperImplTest {

    private List<TourModel> tourModels;

    private List<TourLog> tourLogs;

    private SearchHelper searchHelper;

    @BeforeEach
    private void initSearchHelper(){
        this.searchHelper = new SearchHelperImpl();
    }

    @BeforeEach
    private void initTourModelsList(){
        tourModels = new ArrayList<>();
        tourModels.add(new TourModel(1, "Title", "This is a test tour", "Vienna", "Graz", TransportType.Bicycle, 180.0, LocalTime.of(2,30,1), null));
        tourModels.add(new TourModel(2, "Title2", "Going on holiday", "Vienna", "Milan", TransportType.Car, 846.0, LocalTime.of(8,45,6), null));
        tourModels.add(new TourModel(3, "Title3", "Small walk", "Vienna", "Raasdorf", TransportType.Foot, 8.0, LocalTime.of(2,12,11), "Test.jpeg"));

    }

    @BeforeEach
    private void initTourLogsList(){
        tourLogs = new ArrayList<>();
        tourLogs.add(new TourLog(1, LocalDateTime.of(2022,7,3,17,5), "Nice Tour", Difficulty.Advanced, LocalTime.of(22,2,2), Rating.Bad, 1));
        tourLogs.add(new TourLog(2, LocalDateTime.of(2022,3,23,7,5), "Long and boring", Difficulty.Beginner, LocalTime.of(12,22,32), Rating.Good, 3));
        tourLogs.add(new TourLog(3, LocalDateTime.of(2021,3,4,14,2), "Quick and easy", Difficulty.Expert, LocalTime.of(1,27,29), Rating.Neutral, 2));

    }

    @DisplayName("Calculate Average Time")
    @ParameterizedTest()
    @MethodSource("SearchTextToursProvider")
    void searchTours(String searchText, List<Integer> expectedTourIds) {
        var searchResult = searchHelper.searchTours(tourModels, searchText);
        Collections.sort(searchResult);

        System.out.println(searchResult);
        assertThat(searchResult.size()).isEqualTo(expectedTourIds.size());
        for(int i = 0; i<searchResult.size();i++){
            assertThat(searchResult.get(i)).isEqualTo(expectedTourIds.get(i));
        }
    }
    public static Stream<Arguments> SearchTextToursProvider() {
        return Stream.of(
                Arguments.of("Title", List.of(1,2,3)),
                Arguments.of("TOUR", List.of(1)),
                Arguments.of("Car Foot", List.of(2,3)),
                Arguments.of("", List.of(1,2,3)),
                Arguments.of("Long and boring", List.of())
        );
    }

    @DisplayName("Calculate Average Time")
    @ParameterizedTest()
    @MethodSource("SearchTextLogsProvider")
    void searchLogs(String searchText, List<Integer> expectedTourIds) {
        var searchResult = searchHelper.searchLogs(tourLogs, searchText);
        Collections.sort(searchResult);

        System.out.println(searchResult);
        assertThat(searchResult.size()).isEqualTo(expectedTourIds.size());
        for(int i = 0; i<searchResult.size();i++){
            assertThat(searchResult.get(i)).isEqualTo(expectedTourIds.get(i));
        }
    }

    public static Stream<Arguments> SearchTextLogsProvider() {
        return Stream.of(
                Arguments.of("Long and boring", List.of(2,3)),
                Arguments.of("Beginner", List.of(3)),
                Arguments.of("Good", List.of(3)),
                Arguments.of("Quick Neutral Bad", List.of(1,2))
        );
    }


}