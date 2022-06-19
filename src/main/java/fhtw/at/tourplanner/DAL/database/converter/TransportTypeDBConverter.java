package fhtw.at.tourplanner.DAL.database.converter;

import fhtw.at.tourplanner.DAL.model.enums.TransportType;

public class TransportTypeDBConverter {
    public static TransportType ConvertString(String dbTransportType) {
        if(dbTransportType == null)
            return null;

        switch (dbTransportType) {
            case "Foot":
                return TransportType.Foot;
            case "Bicycle":
                return TransportType.Bicycle;
            case "Car":
                return TransportType.Car;
        }
        throw new IllegalArgumentException();
    }
}
