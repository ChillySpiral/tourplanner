package fhtw.at.tourplanner.DAL.mapQuestAPI.converter;

import fhtw.at.tourplanner.DAL.mapQuestAPI.enums.RouteType;
import fhtw.at.tourplanner.DAL.model.enums.TransportType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TransportTypeConverterTest {

    @DisplayName("Convert Transport Type")
    @ParameterizedTest()
    @MethodSource("TransportTypeProvider")
    void convert(TransportType input, RouteType expected) {
        var actual = TransportTypeConverter.Convert(input);
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> TransportTypeProvider() {
        return Stream.of(
                Arguments.of(TransportType.Foot, RouteType.pedestrian),
                Arguments.of(TransportType.Bicycle, RouteType.bicycle),
                Arguments.of(TransportType.Car, RouteType.fastest)
        );
    }
}