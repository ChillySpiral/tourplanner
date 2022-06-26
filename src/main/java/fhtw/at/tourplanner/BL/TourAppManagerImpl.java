package fhtw.at.tourplanner.BL;

import fhtw.at.tourplanner.BL.pdfGenerator.ReportGenerator;
import fhtw.at.tourplanner.DAL.DalFactory;
import fhtw.at.tourplanner.DAL.dao.Dao;
import fhtw.at.tourplanner.DAL.dao.extended.TourDaoExtension;
import fhtw.at.tourplanner.DAL.mapQuestAPI.MapQuestRepository;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;

import java.time.LocalTime;
import java.util.List;

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

    private boolean mapQuestQueryNecessary(TourModel newValue, TourModel oldValue){
        if(newValue.getTo().isEmpty() || newValue.getFrom().isEmpty())
            return false;

        if(newValue.getFrom() == oldValue.getFrom() && newValue.getTo() == oldValue.getTo() && newValue.getTransportType() == oldValue.getTransportType())
            return false;

        return true;
    }
}
