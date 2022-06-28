package fhtw.at.tourplanner.BL;

import fhtw.at.tourplanner.BL.pdfGenerator.ReportGenerator;
import fhtw.at.tourplanner.DAL.DalFactory;
import fhtw.at.tourplanner.DAL.dao.Dao;
import fhtw.at.tourplanner.DAL.dao.extended.TourDaoExtension;
import fhtw.at.tourplanner.DAL.mapQuestAPI.MapQuestRepository;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.export.exportTourModel;
import fhtw.at.tourplanner.DAL.model.fileSystem.Pair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TourAppManagerImpl implements TourAppManager {

    private final TourDaoExtension tourModelDao;
    private final Dao<TourLog> tourLogDao;
    private final ReportGenerator reportGenerator;
    private final MapQuestRepository mapQuestRepository;

    public TourAppManagerImpl(ReportGenerator reportGenerator, MapQuestRepository mapQuestRepository){
        tourModelDao = DalFactory.GetTourModelDao();
        tourLogDao = DalFactory.GetTourLogDao();
        this.reportGenerator = reportGenerator;
        this.mapQuestRepository = mapQuestRepository;
    }

    @Override
    public List<TourModel> getAllTours() {
        return tourModelDao.getAll();
    }

    @Override
    public TourModel getTour(int Id) {
        var result = tourModelDao.get(Id);

        if(result.isPresent()){
            return result.get();
        }
        return null;
    }

    @Override
    public TourModel createTour() {
        return tourModelDao.create(-1);
    }

    @Override
    public void deleteTour(TourModel tourModel) {
        tourModelDao.delete(tourModel);
    }

    @Override
    public void updateTour(TourModel tourModel) {

        var dbTour = getTour(tourModel.getTourId());

        if(dbTour == null)
            throw new NullPointerException("Tour not found in DB");

        if(mapQuestQueryNecessary(tourModel, dbTour)){
            var result = mapQuestRepository.getRouteImage(tourModel);
            if(result != null){
                tourModel.setTourDistance(Double.parseDouble(result.bObject.getDistance()));
                tourModel.setEstimatedTime(LocalTime.parse(result.bObject.getFormattedTime()));
                tourModel.setImageFilename(result.aObject);
            }
        }

        tourModelDao.update(tourModel);
    }

    @Override
    public List<TourLog> getAllTourLogsForTour(TourModel tourModel) {
         return tourModelDao.getLogsForTour(tourModel);
    }

    @Override
    public List<TourLog> getAllTourLogs() {
        return tourLogDao.getAll();
    }

    @Override
    public TourLog createLog(int tourId) {
        return tourLogDao.create(tourId);
    }

    @Override
    public void deleteLog(TourLog log) {
        tourLogDao.delete(log);
    }

    @Override
    public void updateLog(TourLog log) {
        tourLogDao.update(log);
    }

    @Override
    public void generateTourReport(TourModel tourModel) {
        var tour = getTour(tourModel.getTourId());
        if(tour != null){
            var logs = getAllTourLogsForTour(tour);
            reportGenerator.generateReport(tour, logs);
        }
    }

    @Override
    public void generateSummaryReport() {
        var tours = getAllTours();
        List<Pair<TourModel, List<TourLog>>> result = new ArrayList<>();

        for (var tour: tours) {
            var logs = getAllTourLogsForTour(tour);
            var tmpPair = new Pair<>(tour, logs);
            result.add(tmpPair);
        }

        reportGenerator.generateSummary(result);
    }

    @Override
    public void exportTour(TourModel tourModel) {
        var logs = getAllTourLogsForTour(tourModel);
        var export = new exportTourModel(tourModel, logs);
        try{
        writeJSON(export);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void importTour(File importFile) {
        try {
            var importModel = readJSON();
            var newTour = createTour();
            newTour.setTitle(importModel.getTour().getTitle());
            newTour.setTransportType(importModel.getTour().getTransportType());
            newTour.setFrom(importModel.getTour().getFrom());
            newTour.setTo(importModel.getTour().getTo());
            newTour.setDescription(importModel.getTour().getDescription());
            updateTour(newTour);

            for (var log: importModel.tourLogs) {
                var newLog = createLog(newTour.getTourId());
                newLog.setDateTime(log.getDateTime());
                newLog.setTotalTime(log.getTotalTime());
                newLog.setComment(log.getComment());
                newLog.setDifficulty(log.getDifficulty());
                newLog.setRating(log.getRating());
                updateLog(newLog);
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean mapQuestQueryNecessary(TourModel newValue, TourModel oldValue){
        if(newValue.getTo().isEmpty() || newValue.getFrom().isEmpty())
            return false;

        if(newValue.getFrom() == oldValue.getFrom() && newValue.getTo() == oldValue.getTo() && newValue.getTransportType() == oldValue.getTransportType())
            return false;

        return true;
    }

    private void writeJSON(exportTourModel exportTour) throws JsonGenerationException, JsonMappingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.writeValue(new File("./export/exportTour.json"), exportTour);
    }

    private exportTourModel readJSON() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        exportTourModel student = mapper.readValue(new File("./export/exportTour.json"), exportTourModel.class);
        return student;
    }

}
