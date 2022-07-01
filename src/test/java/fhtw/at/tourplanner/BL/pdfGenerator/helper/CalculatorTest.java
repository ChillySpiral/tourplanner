package fhtw.at.tourplanner.BL.pdfGenerator.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorTest {

    @DisplayName("Calculate Average Time")
    @ParameterizedTest()
    @MethodSource("TimesListProvider")
    void calculateAverageTime(List<LocalTime> times, LocalTime expected) {
        var actual = Calculator.calculateAverageTime(times);
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> TimesListProvider() {
        return Stream.of(
                Arguments.of(List.of(LocalTime.of(10,0,0), LocalTime.of(5,0,0)), LocalTime.of(7,30,0)),
                Arguments.of(List.of(LocalTime.of(10,30,0), LocalTime.of(5,15,0)), LocalTime.of(7,52,30)),
                Arguments.of(List.of(LocalTime.of(10,0,0), LocalTime.of(5,0,0), LocalTime.of(0,0,0)), LocalTime.of(5,0,0))
        );
    }
}