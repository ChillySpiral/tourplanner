package fhtw.at.tourplanner.DAL.database.converter;

import fhtw.at.tourplanner.DAL.model.enums.TransportType;
import lombok.extern.log4j.Log4j2;

@Log4j2
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
        log.fatal("TransportTypeDBConverter failed because an illegal argument [name: " + dbTransportType + " ] was used."); // TODO: ok?
        throw new IllegalArgumentException();
    }
}
