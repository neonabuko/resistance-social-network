package com.api.resistancesocialnetwork.rules;


import com.api.resistancesocialnetwork.model.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationUpdateRules {

    private final DataFormatRules dataFormatRules = new DataFormatRules();
    public Location checkIfLocationIsValid(Double latitude, Double longitude, String base) {
        Double formattedLatitude = dataFormatRules.formatCoordinate(latitude, 90);
        Double formattedLongitude = dataFormatRules.formatCoordinate(longitude, 180);
        String formattedBase = dataFormatRules.formatBase(base);

        return new Location(formattedLatitude, formattedLongitude, formattedBase);
    }
}
