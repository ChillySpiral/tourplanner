package fhtw.at.tourplanner.BL.appManager.implementation;

import fhtw.at.tourplanner.BL.appManager.TourAppManager;
import fhtw.at.tourplanner.BL.jsonGenerator.JsonGenerator;
import fhtw.at.tourplanner.BL.pdfGenerator.ReportGenerator;
import fhtw.at.tourplanner.DAL.dao.Dao;
import fhtw.at.tourplanner.DAL.dao.extended.TourDaoExtension;
import fhtw.at.tourplanner.DAL.mapQuestAPI.MapQuestRepository;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.enums.TransportType;
import fhtw.at.tourplanner.DAL.model.fileSystem.Pair;
import fhtw.at.tourplanner.DAL.model.mapQuestModels.Route;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class TourAppManagerImplTest {

    @Mock
    ReportGenerator reportGenerator;

    @Mock
    MapQuestRepository mapQuestRepository;

    @Mock
    JsonGenerator jsonGenerator;

    @Mock
    TourDaoExtension tourDao;

    @Mock
    Dao<TourLog> tourLogDao;

    private TourAppManager tourAppManager;
    private List<TourModel> tourModels;

    private List<TourLog> tourLogs;

    @BeforeEach
    private void initTourAppManager(){
        tourAppManager = new TourAppManagerImpl(reportGenerator, mapQuestRepository, jsonGenerator, tourDao, tourLogDao);
    }

    @BeforeEach
    private void initTourModelsList(){
        tourModels = new ArrayList<>();
        tourModels.add(new TourModel(1, "Title", "Description", "Vienna", "Graz", TransportType.Car, 100.0, LocalTime.of(3,2,1), null));
        tourModels.add(new TourModel(2, "Title 2", "Description 2", "Vienna 2", "Graz 2", TransportType.Bicycle, 100.0, LocalTime.of(8,7,6), null));
        tourModels.add(new TourModel(3, "Title 3", "Description 3", "Vienna 3", "Graz 3", TransportType.Foot, 100.0, LocalTime.of(13,12,11), "Test.jpeg"));

    }

    @BeforeEach
    private void initTourLogsList(){
        tourLogs = new ArrayList<>();
        tourLogs.add(new TourLog(1, LocalDateTime.of(2022,7,3,17,5), "Comment 1", "Hard", LocalTime.of(22,2,2), "Great 1", 1));
        tourLogs.add(new TourLog(2, LocalDateTime.of(2022,3,23,7,5), "Comment 2", "Easy", LocalTime.of(12,22,32), "Great 2", 1));
        tourLogs.add(new TourLog(3, LocalDateTime.of(2021,3,4,14,2), "Comment 3", "Medium", LocalTime.of(1,27,29), "Great 3", 1));

    }

    @Test
    void getAllTours() {
        Mockito.when(tourDao.getAll()).thenReturn(tourModels);
        var actual = tourAppManager.getAllTours();

        assertThat(actual.size()).isEqualTo(tourModels.size());
        assertThat(actual).usingRecursiveComparison().isEqualTo(tourModels);
        Mockito.verify(tourDao, Mockito.times(1)).getAll();
    }

    @Test
    void getTour() {
        Mockito.when(tourDao.get(1)).thenReturn(Optional.empty());
        Mockito.when(tourDao.get(2)).thenReturn(Optional.of(new TourModel()));

        var tour1 = tourAppManager.getTour(1);
        var tour2 = tourAppManager.getTour(2);

        assertThat(tour1).isNull();
        assertThat(tour2).isNotNull();
        assertThat(tour2).usingRecursiveComparison().isEqualTo(new TourModel());
        Mockito.verify(tourDao, Mockito.times(1)).get(1);
        Mockito.verify(tourDao, Mockito.times(1)).get(2);
    }

    @Test
    void createTour() {
        Mockito.when(tourDao.create(-1)).thenReturn(new TourModel());
        var newTour = tourAppManager.createTour();
        Mockito.verify(tourDao, Mockito.times(1)).create(-1);
        assertThat(newTour).isNotNull();
    }

    @Test
    void deleteTour() {
        var tour = new TourModel();
        tourAppManager.deleteTour(tour);
        Mockito.verify(tourDao, Mockito.times(1)).delete(tour);
    }

    @Test
    void updateTour() {
        var tour = tourModels.get(0);

        var route = new Route();
        route.setDistance("300.0");
        route.setFormattedTime("12:12:12");
        var filename = "./filename.jpg";
        var mapQuestReturn = new Pair<>(filename, route);

        Mockito.when(mapQuestRepository.getRouteImage(tour)).thenReturn(mapQuestReturn);
        Mockito.when(tourDao.get(tour.getTourId())).thenReturn(Optional.of(tourModels.get(1)));

        tourAppManager.updateTour(tour);

        assertThat(tour.getTourId()).isEqualTo(tourModels.get(0).getTourId());
        assertThat(tour.getTourDistance()).isEqualTo(300.0);
        assertThat(tour.getEstimatedTime()).isEqualTo(LocalTime.of(12,12,12));
        assertThat(tour.getImageFilename()).isEqualTo(filename);
        Mockito.verify(mapQuestRepository, Mockito.times(1)).getRouteImage(tour);
        Mockito.verify(tourDao,Mockito.times(1)).get(tour.getTourId());
    }

    @Test
    void getAllTourLogsForTour() {
        var tour = tourModels.get(0);
        Mockito.when(tourDao.getLogsForTour(tour)).thenReturn(tourLogs);

        var actual = tourAppManager.getAllTourLogsForTour(tour);
        assertThat(actual.size()).isEqualTo(tourLogs.size());
        assertThat(actual).usingRecursiveComparison().isEqualTo(tourLogs);
        Mockito.verify(tourDao, Mockito.times(1)).getLogsForTour(tour);
    }

    @Test
    void getAllTourLogs() {
        Mockito.when(tourLogDao.getAll()).thenReturn(tourLogs);

        var actual = tourAppManager.getAllTourLogs();
        assertThat(actual.size()).isEqualTo(tourLogs.size());
        assertThat(actual).usingRecursiveComparison().isEqualTo(tourLogs);
        Mockito.verify(tourLogDao, Mockito.times(1)).getAll();
    }

    @Test
    void createLog() {
        Mockito.when(tourLogDao.create(1)).thenReturn(tourLogs.get(0));

        var actual = tourAppManager.createLog(1);
        assertThat(actual).usingRecursiveComparison().isEqualTo(tourLogs.get(0));
        Mockito.verify(tourLogDao, Mockito.times(1)).create(1);
    }

    @Test
    void deleteLog() {
        var log = tourLogs.get(0);

        tourAppManager.deleteLog(log);
        Mockito.verify(tourLogDao, Mockito.times(1)).delete(log);
    }

    @Test
    void updateLog() {
        var log = tourLogs.get(0);

        tourAppManager.updateLog(log);
        Mockito.verify(tourLogDao, Mockito.times(1)).update(log);
    }

    @Test
    void generateTourReport() {
        //ToDo: Implement
        fail();
    }

    @Test
    void generateSummaryReport() {
        //ToDo: Implement
        fail();
    }

    @Test
    void exportTour() {
        //ToDo: Implement
        fail();
    }

    @Test
    void importTour() {
        //ToDo: Implement
        fail();
    }
}