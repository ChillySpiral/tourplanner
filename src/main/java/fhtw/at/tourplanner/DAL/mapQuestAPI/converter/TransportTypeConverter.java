package fhtw.at.tourplanner.DAL.mapQuestAPI.converter;

import fhtw.at.tourplanner.DAL.mapQuestAPI.enums.RouteType;
import fhtw.at.tourplanner.DAL.model.enums.TransportType;

public class TransportTypeConverter {

    public static RouteType Convert(TransportType transportType){
        if(transportType == null)
            return RouteType.shortest;

        switch (transportType){
            case Foot -> {
                return RouteType.pedestrian;
            }
            case Bicycle -> {
                return RouteType.bicycle;
            }
            case Car -> {
                return RouteType.fastest;
            }
        }
        throw new IllegalArgumentException();
    }
}
