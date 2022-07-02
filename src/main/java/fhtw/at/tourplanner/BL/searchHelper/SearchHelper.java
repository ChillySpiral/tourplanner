package fhtw.at.tourplanner.BL.searchHelper;

import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;

import java.util.List;

public interface SearchHelper {
    List<Integer> searchTours(List<TourModel> tourModelList, String searchText);
    List<Integer> searchLogs(List<TourLog> tourLogList, String searchText);
}
