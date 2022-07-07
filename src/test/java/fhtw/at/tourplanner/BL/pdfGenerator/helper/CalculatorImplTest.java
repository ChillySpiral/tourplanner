package fhtw.at.tourplanner.BL.pdfGenerator.helper;

import fhtw.at.tourplanner.BL.calculator.Calculator;
import fhtw.at.tourplanner.BL.calculator.implementation.CalculatorImpl;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.enums.Difficulty;
import fhtw.at.tourplanner.DAL.model.enums.Rating;
import fhtw.at.tourplanner.DAL.model.enums.TransportType;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
class CalculatorImplTest {

    private static List<TourModel> tourModels;

    private List<TourLog> tourLogs;


    private Calculator calculator;

    @BeforeEach
    private void initTourModelsList() {
        tourModels = new ArrayList<>();
        tourModels.add(new TourModel(1, "Title", "This is a test tour", "Vienna", "Graz", TransportType.Car, 180.0, LocalTime.of(2, 30, 1), null));
        tourModels.add(new TourModel(2, "Title2", "Going on holiday", "Vienna", "Milan", TransportType.Car, 846.0, LocalTime.of(8, 45, 6), null));
        tourModels.add(new TourModel(3, "Title3", "Small walk", "Vienna", "Raasdorf", TransportType.Foot, 8.0, LocalTime.of(2, 12, 11), "Test.jpeg"));

    }

    @BeforeEach
    private void initTourLogsList() {
        tourLogs = new ArrayList<>();
        tourLogs.add(new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "Nice Tour", Difficulty.Advanced, LocalTime.of(8, 2, 2), Rating.Bad, 1));
        tourLogs.add(new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "Long and boring", Difficulty.Beginner, LocalTime.of(7, 22, 32), Rating.Good, 3));
        tourLogs.add(new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "Quick and easy", Difficulty.Expert, LocalTime.of(3, 27, 29), Rating.Neutral, 2));

    }

    @BeforeEach
    private void initCalculator() {
        this.calculator = new CalculatorImpl();
    }

    @DisplayName("Calculate Average Time")
    @ParameterizedTest()
    @MethodSource("TimesListProvider")
    void calculateAverageTime(List<LocalTime> times, LocalTime expected) {
        var actual = calculator.calculateAverageTime(times);
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> TimesListProvider() {
        return Stream.of(
                Arguments.of(List.of(LocalTime.of(10, 0, 0), LocalTime.of(5, 0, 0)), LocalTime.of(7, 30, 0)),
                Arguments.of(List.of(LocalTime.of(10, 30, 0), LocalTime.of(5, 15, 0)), LocalTime.of(7, 52, 30)),
                Arguments.of(List.of(LocalTime.of(10, 0, 0), LocalTime.of(5, 0, 0), LocalTime.of(0, 0, 0)), LocalTime.of(5, 0, 0))
        );
    }

    @DisplayName("Calculate Average Difficulty")
    @ParameterizedTest()
    @MethodSource("DifficultyListProvider")
    void calculateAverageDifficulty(List<Difficulty> difficulties, Difficulty expected) {
        var actual = calculator.calculateAverageDifficulty(difficulties);
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> DifficultyListProvider() {
        return Stream.of(
                Arguments.of(List.of(Difficulty.Beginner,Difficulty.Expert), Difficulty.Advanced),
                Arguments.of(List.of(Difficulty.Beginner,Difficulty.Intermediate,Difficulty.Advanced), Difficulty.Intermediate),
                Arguments.of(List.of(Difficulty.Intermediate,Difficulty.Intermediate,Difficulty.Advanced,Difficulty.Expert),Difficulty.Advanced)
        );
    }

    @DisplayName("Calculate Average Rating")
    @ParameterizedTest()
    @MethodSource("RatingListProvider")
    void calculateAverageRating(List<Rating> ratings, Rating expected) {
        var actual = calculator.calculateAverageRating(ratings);
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> RatingListProvider() {
        return Stream.of(
                Arguments.of(List.of(Rating.Terrible,Rating.Perfect), Rating.Neutral),
                Arguments.of(List.of(Rating.Terrible,Rating.Bad, Rating.Neutral), Rating.Bad),
                Arguments.of(List.of(Rating.Bad,Rating.Perfect,Rating.Good,Rating.Neutral),Rating.Neutral)
        );
    }


    @DisplayName("Calculate Popularity")
    @ParameterizedTest()
    @MethodSource("PopularityProvider")
    void calculatePopularity(int allLogsSize, int tourLogsSize, String expected) {
        var actual = calculator.calculatePopularity(allLogsSize, tourLogsSize);
        assertThat(actual).isNotNull();
        assertThat(actual).isNotEmpty();
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> PopularityProvider() {
        return Stream.of(
                Arguments.of(0, 10, "Not popular."),
                Arguments.of(10, 6, "The most popular! Over 50 % of all tour logs are for this tour."),
                Arguments.of(10, 5, "Very popular! 50 % of tour all logs are for this tour."),
                Arguments.of(8, 3, "Very popular! Over 33.33 % of all tour logs are for this tour."),
                Arguments.of(9, 3, "Very popular! Over 33.33 % of all tour logs are for this tour."),
                Arguments.of(8, 2, "Very popular! Over 25 % of all tour logs are for this tour."),
                Arguments.of(10, 2, "Fairly popular! Over 20 % of all tour logs are for this tour."),
                Arguments.of(10, 1, "Somewhat popular. Over 10 % of all tour logs are for this tour."),
                Arguments.of(20, 1, "Slightly popular. Over 5 % of all tour logs are for this tour."),
                Arguments.of(21, 1, "Not very popular. Less than 5 % of all tour logs are for this tour.")
        );
    }

    @DisplayName("Calculate ChildFriendliness")
    @ParameterizedTest()
    @MethodSource("FriendlinessProvider")
    void calculateChildFriendliness(List<TourLog> logs, TourModel tourModel, String expected) {
        var actual = calculator.calculateChildFriendliness(tourModel, logs);
        assertThat(actual).isNotNull();
        assertThat(actual).isNotEmpty();
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> FriendlinessProvider() {
        return Stream.of(
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Advanced, LocalTime.of(8, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Beginner, LocalTime.of(7, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Expert, LocalTime.of(3, 27, 29), Rating.Neutral, 2)
                ), null, "Not enough data."),
                Arguments.of(null, new TourModel(1, "", "", "", "", TransportType.Car, 180.0, LocalTime.now(), ""), "Not enough data."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Beginner, LocalTime.of(4, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Beginner, LocalTime.of(4, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Beginner, LocalTime.of(4, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Car, 180.0, LocalTime.now(), ""), "Child friendly."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Beginner, LocalTime.of(4, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Beginner, LocalTime.of(4, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Beginner, LocalTime.of(4, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Car, 600.0, LocalTime.now(), ""), "Child friendly."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Beginner, LocalTime.of(4, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Beginner, LocalTime.of(4, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Beginner, LocalTime.of(4, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Car, 601.0, LocalTime.now(), ""), "For advanced or older children."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Beginner, LocalTime.of(2, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Beginner, LocalTime.of(2, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Beginner, LocalTime.of(2, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Bicycle, 30.0, LocalTime.now(), ""), "Child friendly."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Beginner, LocalTime.of(2, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Beginner, LocalTime.of(2, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Beginner, LocalTime.of(2, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Bicycle, 31.0, LocalTime.now(), ""), "Child friendly."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Beginner, LocalTime.of(2, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Beginner, LocalTime.of(2, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Beginner, LocalTime.of(2, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Bicycle, 61.0, LocalTime.now(), ""), "For advanced or older children."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Beginner, LocalTime.of(2, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Beginner, LocalTime.of(2, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Beginner, LocalTime.of(2, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Foot, 10.0, LocalTime.now(), ""), "Child friendly."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Beginner, LocalTime.of(2, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Beginner, LocalTime.of(2, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Beginner, LocalTime.of(2, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Foot, 11.0, LocalTime.now(), ""), "Child friendly."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Beginner, LocalTime.of(2, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Beginner, LocalTime.of(2, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Beginner, LocalTime.of(2, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Foot, 21.0, LocalTime.now(), ""), "For advanced or older children."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Advanced, LocalTime.of(4, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Advanced, LocalTime.of(4, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Advanced, LocalTime.of(4, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Car, 180.0, LocalTime.now(), ""), "Child friendly."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Advanced, LocalTime.of(4, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Advanced, LocalTime.of(4, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Advanced, LocalTime.of(4, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Car, 600.0, LocalTime.now(), ""), "For advanced or older children."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Expert, LocalTime.of(4, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Expert, LocalTime.of(4, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Expert, LocalTime.of(4, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Car, 601.0, LocalTime.now(), ""), "Not suitable for children."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Advanced, LocalTime.of(2, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Advanced, LocalTime.of(2, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Advanced, LocalTime.of(2, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Bicycle, 30.0, LocalTime.now(), ""), "Child friendly."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Advanced, LocalTime.of(2, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Advanced, LocalTime.of(2, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Advanced, LocalTime.of(2, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Bicycle, 31.0, LocalTime.now(), ""), "For advanced or older children."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Expert, LocalTime.of(2, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Expert, LocalTime.of(2, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Expert, LocalTime.of(2, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Bicycle, 61.0, LocalTime.now(), ""), "Not suitable for children."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Advanced, LocalTime.of(2, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Advanced, LocalTime.of(2, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Advanced, LocalTime.of(2, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Foot, 10.0, LocalTime.now(), ""), "Child friendly."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Advanced, LocalTime.of(2, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Advanced, LocalTime.of(2, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Advanced, LocalTime.of(2, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Foot, 11.0, LocalTime.now(), ""), "For advanced or older children."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Expert, LocalTime.of(2, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Expert, LocalTime.of(2, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Expert, LocalTime.of(2, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Foot, 21.0, LocalTime.now(), ""), "Not suitable for children."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Advanced, LocalTime.of(5, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Advanced, LocalTime.of(5, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Advanced, LocalTime.of(5, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Car, 180.0, LocalTime.now(), ""), "For advanced or older children."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Advanced, LocalTime.of(5, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Advanced, LocalTime.of(5, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Advanced, LocalTime.of(5, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Car, 600.0, LocalTime.now(), ""), "For advanced or older children."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Advanced, LocalTime.of(8, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Advanced, LocalTime.of(8, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Advanced, LocalTime.of(8, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Car, 601.0, LocalTime.now(), ""), "Not suitable for children."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Advanced, LocalTime.of(3, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Advanced, LocalTime.of(3, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Advanced, LocalTime.of(3, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Bicycle, 30.0, LocalTime.now(), ""), "For advanced or older children."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Advanced, LocalTime.of(3, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Advanced, LocalTime.of(3, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Advanced, LocalTime.of(3, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Bicycle, 31.0, LocalTime.now(), ""), "For advanced or older children."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Advanced, LocalTime.of(6, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Advanced, LocalTime.of(6, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Advanced, LocalTime.of(6, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Bicycle, 61.0, LocalTime.now(), ""), "Not suitable for children."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Advanced, LocalTime.of(4, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Advanced, LocalTime.of(4, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Advanced, LocalTime.of(4, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Foot, 10.0, LocalTime.now(), ""), "For advanced or older children."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Advanced, LocalTime.of(4, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Advanced, LocalTime.of(4, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Advanced, LocalTime.of(4, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Foot, 11.0, LocalTime.now(), ""), "For advanced or older children."),
                Arguments.of(List.of(
                        new TourLog(1, LocalDateTime.of(2022, 7, 3, 17, 5), "", Difficulty.Advanced, LocalTime.of(8, 2, 2), Rating.Bad, 1),
                        new TourLog(2, LocalDateTime.of(2022, 3, 23, 7, 5), "", Difficulty.Advanced, LocalTime.of(8, 22, 32), Rating.Good, 3),
                        new TourLog(3, LocalDateTime.of(2021, 3, 4, 14, 2), "", Difficulty.Advanced, LocalTime.of(8, 27, 29), Rating.Neutral, 2)
                ), new TourModel(1, "", "", "", "", TransportType.Foot, 21.0, LocalTime.now(), ""), "Not suitable for children.")
        );
    }
}