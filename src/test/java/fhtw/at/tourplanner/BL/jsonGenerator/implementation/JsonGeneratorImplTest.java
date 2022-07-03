package fhtw.at.tourplanner.BL.jsonGenerator.implementation;

import fhtw.at.tourplanner.BL.jsonGenerator.JsonGenerator;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.enums.Difficulty;
import fhtw.at.tourplanner.DAL.model.enums.Rating;
import fhtw.at.tourplanner.DAL.model.enums.TransportType;
import fhtw.at.tourplanner.DAL.model.export.exportTourModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

class JsonGeneratorImplTest {

    @Test
    @DisplayName("Export & Import Json")
    public void exportImportTest(){
        var tour = new TourModel();

        tour.setTourId(1);
        tour.setTitle("Tour Number One");
        tour.setDescription("This is a tour");
        tour.setTo("Vienna");
        tour.setFrom("Schladming");
        tour.setTourDistance(123.0);
        tour.setTransportType(TransportType.Car);
        tour.setEstimatedTime(LocalTime.of(3,20,55));
        tour.setImageFilename(null);

        var log1 = new TourLog();
        log1.setLogId(1);
        log1.setTourId(1);
        log1.setRating(Rating.Neutral);
        log1.setDateTime(LocalDateTime.of(2022,03,27,12,12));
        log1.setDifficulty(Difficulty.Expert);
        log1.setComment("Very difficult Route");
        log1.setTotalTime(LocalTime.of(4,12,33));

        var log2 = new TourLog();
        log2.setLogId(1);
        log2.setTourId(2);
        log2.setRating(Rating.Bad);
        log2.setDateTime(LocalDateTime.of(2022,2,4,12,12));
        log2.setDifficulty(Difficulty.Advanced);
        log2.setComment("Very easy Route");
        log2.setTotalTime(LocalTime.of(2,3,2));

        List<TourLog> testLogList = new ArrayList<>();
        testLogList.add(log1);
        testLogList.add(log2);

        var testModel = new exportTourModel();
        testModel.setTour(tour);
        testModel.setTourLogs(testLogList);

        JsonGenerator jsonGen = new JsonGeneratorImpl();

        File testFile = new File("./test.json");

        try{
            jsonGen.writeJSON(testFile, testModel);
        } catch(Exception e){
            e.printStackTrace();
            fail();
        }

        assertThat(testFile.exists());

        exportTourModel result = new exportTourModel();
        try{
            result = jsonGen.readJSON(testFile);
        } catch(Exception e){
            e.printStackTrace();
            fail();
        }

        compareExportTourModels(testModel, result);

        testFile.delete();
    }

    private void compareExportTourModels(exportTourModel expected, exportTourModel actual){
        assertThat(expected).isNotNull();
        assertThat(actual).isNotNull();

        assertThat(expected.tour).isNotNull();
        assertThat(actual.tour).isNotNull();
        assertThat(expected.tourLogs).isNotNull();
        assertThat(actual.tourLogs).isNotNull();

        var expectedTour = expected.getTour();
        var actualTour = actual.getTour();

        assertThat(actualTour).usingRecursiveComparison().isEqualTo(expectedTour);
        assertThat(actual.getTourLogs().size()).isEqualTo(expected.getTourLogs().size());
        assertThat(actual.getTourLogs().get(0)).usingRecursiveComparison().isEqualTo(expected.getTourLogs().get(0));
        assertThat(actual.getTourLogs().get(1)).usingRecursiveComparison().isEqualTo(expected.getTourLogs().get(1));
    }
}