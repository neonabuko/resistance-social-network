package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationUpdateRulesTest {
    private final LocationUpdateRules locationUpdateRules = new LocationUpdateRules();

    @Test
    void should_set_latitude_to_minus_90_when_under_negative_90() {
        Double latitude = -123.2;
        Double longitude = 23.2;
        String base = "base";

        Location formattedLocation = locationUpdateRules.checkIfLocationIsValid(latitude, longitude, base);
        assertEquals(-90, formattedLocation.getLatitude());
    }

    @Test
    void should_set_latitude_to_90_when_over_90() {
        Double latitude = 200.2;
        Double longitude = 20.20;
        String base = "auau";

        Location formattedLocation = locationUpdateRules.checkIfLocationIsValid(latitude, longitude, base);
        assertEquals(90, formattedLocation.getLatitude());
    }

    @Test
    void should_set_longitude_to_minus_180_when_under_negative_180() {
        Double latitude = 34.3;
        Double longitude = -2347.20;
        String base = "auau";

        Location formattedLocation = locationUpdateRules.checkIfLocationIsValid(latitude, longitude, base);
        assertEquals(-180, formattedLocation.getLongitude());
    }

    @Test
    void should_set_longitude_to_180_when_over_180() {
        Double latitude = 30.2;
        Double longitude = 300.20;
        String base = "auauuu";

        Location formattedLocation = locationUpdateRules.checkIfLocationIsValid(latitude, longitude, base);
        assertEquals(180, formattedLocation.getLongitude());
    }

    @Test
    void should_return_base_as_undefined_if_not_provided() {
        Exception e = assertThrows(Exception.class, () ->
                locationUpdateRules.checkIfLocationIsValid(43.3, 3.2, null)
        );
        assertTrue(e.getMessage().contains("must provide base"));
    }

    @Test
    void should_return_30char_base_when_over_30char_provided() {
        Double latitude = 30.2;
        Double longitude = 3.3;
        String base = "a".repeat(31);

        Location formattedLocation = locationUpdateRules.checkIfLocationIsValid(latitude, longitude, base);
        assertEquals(30, formattedLocation.getBase().length());
    }
}