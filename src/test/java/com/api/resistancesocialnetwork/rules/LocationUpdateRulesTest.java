package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationUpdateRulesTest {
    private final FormatEntities formatEntities = new FormatEntities(new FormatData());
    private final LocationUpdateRules locationUpdateRules = new LocationUpdateRules(formatEntities);

    @Test
    void should_throw_IllegalArgumentException_when_newLocation_null() {
        Location newLocation = null;
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                locationUpdateRules.handle(newLocation));
        assertTrue(e.getMessage().contains("must provide new location"));
    }

    @Test
    void should_throw_IllegalArgumentException_when_locationId_null() {
        Location newLocation = new Location(22.1, 22.1, "base");
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                locationUpdateRules.handle(newLocation));
        assertTrue(e.getMessage().contains("must provide location id"));
    }

    @Test
    void should_set_latitude_to_minus_90_when_under_negative_90() {
        Location location = new Location(-921.2, 2.2, "base");
        location.setId(1);
        locationUpdateRules.handle(location);
        assertEquals(-90, location.getLatitude());
    }

    @Test
    void should_set_latitude_to_90_when_over_90() {
        Location location = new Location(232.2, 2.2, "base");
        location.setId(1);
        locationUpdateRules.handle(location);
        assertEquals(90, location.getLatitude());
    }

    @Test
    void should_set_longitude_to_minus_180_when_under_negative_180() {
        Location location = new Location(2.2, -1434.2, "base");
        location.setId(1);
        locationUpdateRules.handle(location);
        assertEquals(-180, location.getLongitude());
    }

    @Test
    void should_set_longitude_to_180_when_over_180() {
        Location location = new Location(2.2, 23123.2, "base");
        location.setId(1);
        locationUpdateRules.handle(location);
        assertEquals(180, location.getLongitude());
    }

    @Test
    void should_return_base_as_undefined_if_not_provided() {
        Location location = new Location(23.2, 322.2, null);
        location.setId(1);
        Exception e = assertThrows(Exception.class, () ->
                locationUpdateRules.handle(location)
        );
        assertTrue(e.getMessage().contains("must provide base"));
    }

    @Test
    void should_return_30char_base_when_over_30char_provided() {
        Location location = new Location(2.2, 2.2, "b".repeat(230));
        location.setId(1);
        locationUpdateRules.handle(location);
        assertEquals(30, location.getBase().length());
    }
}