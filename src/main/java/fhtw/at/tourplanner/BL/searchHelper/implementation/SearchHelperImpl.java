package fhtw.at.tourplanner.BL.searchHelper.implementation;

import fhtw.at.tourplanner.BL.searchHelper.SearchHelper;
import fhtw.at.tourplanner.DAL.model.TourLog;
import fhtw.at.tourplanner.DAL.model.TourModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchHelperImpl implements SearchHelper {
    @Override
    public List<Integer> searchTours(List<TourModel> tourModelList, String searchText) {
        if(searchText == null || searchText.isEmpty())
            return tourModelList.stream().map(TourModel::getTourId).collect(Collectors.toList());

        var searchTextSplit = Arrays.stream(searchText.split(" ")).toList();

        List<Integer> result = new ArrayList<>();
        for(var search : searchTextSplit){
            Predicate<TourModel> titlePredicateNull = d -> d.getTitle() != null;
            Predicate<TourModel> descriptionPredicateNull = d -> d.getDescription() != null;
            Predicate<TourModel> fromPredicateNull = d -> d.getFrom() != null;
            Predicate<TourModel> toPredicateNull = d -> d.getTo() != null;
            Predicate<TourModel> transportTypePredicateNull = d -> d.getTransportType() != null;

            Predicate<TourModel> titlePredicate = d -> d.getTitle().toLowerCase().contains(search.toLowerCase());
            Predicate<TourModel> descriptionPredicate = d -> d.getDescription().toLowerCase().contains(search.toLowerCase());
            Predicate<TourModel> fromPredicate = d -> d.getFrom().toLowerCase().contains(search.toLowerCase());
            Predicate<TourModel> toPredicate = d -> d.getTo().toLowerCase().contains(search.toLowerCase());
            Predicate<TourModel> transportTypePredicate = d -> d.getTransportType().toString().toLowerCase().contains(search.toLowerCase());


            List<Integer> tmpResult;
            tmpResult = tourModelList.stream()
                    .filter((titlePredicateNull.and(titlePredicate)).or(descriptionPredicateNull.and(descriptionPredicate)).or(fromPredicateNull.and(fromPredicate)).or(toPredicateNull.and(toPredicate)).or(transportTypePredicateNull.and(transportTypePredicate)))
                    .map(TourModel::getTourId)
                    .collect(Collectors.toList());

            if(!result.isEmpty()){
                result = Stream.of(result, tmpResult)
                        .flatMap(List::stream)
                        .distinct()
                        .collect(Collectors.toList());
            }else{
                result = tmpResult;
            }
        }
        return result;
    }

    @Override
    public List<Integer> searchLogs(List<TourLog> tourLogList, String searchText) {
        if(searchText == null || searchText.isEmpty())
            return tourLogList.stream().map(TourLog::getTourId).collect(Collectors.toList());

        var searchTextSplit = Arrays.stream(searchText.split(" ")).toList();

        List<Integer> result = new ArrayList<>();
        for(var search : searchTextSplit){
            Predicate<TourLog> commentPredicateNull = d -> d.getComment() != null;
            Predicate<TourLog> difficultyPredicateNull = d -> d.getDifficulty() != null;
            Predicate<TourLog> ratingPredicateNull = d -> d.getRating() != null;

            Predicate<TourLog> commentPredicate = d -> d.getComment().toLowerCase().contains(search.toLowerCase());
            Predicate<TourLog> difficultyPredicate = d -> d.getDifficulty().toString().toLowerCase().contains(search.toLowerCase());
            Predicate<TourLog> ratingPredicate = d -> d.getRating().toString().toLowerCase().contains(search.toLowerCase());

            List<Integer> tmpResult;
            tmpResult = tourLogList.stream()
                    .filter((commentPredicateNull.and(commentPredicate)).or(difficultyPredicateNull.and(difficultyPredicate)).or(ratingPredicateNull.and(ratingPredicate)))
                    .map(TourLog::getTourId)
                    .collect(Collectors.toList());

            if(!result.isEmpty()){
                result = Stream.of(result, tmpResult)
                        .flatMap(List::stream)
                        .distinct()
                        .collect(Collectors.toList());
            }else{
                result = tmpResult;
            }
        }
        return result;
    }
}
