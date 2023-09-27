package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.formatters.FormatData;
import com.api.resistancesocialnetwork.formatters.FormatEntities;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.request.facade.LocationUpdateFacade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationUpdateRulesTest {
    private final FormatEntities formatEntities = new FormatEntities(new FormatData());
    private final LocationUpdateRules locationUpdateRules = new LocationUpdateRules(formatEntities);

    @Test
    void should_throw_IllegalStateException_when_newLocation_null() {
        Location newLocation = null;
        LocationUpdateFacade locationUpdateFacade = new LocationUpdateFacade(newLocation);
        
        Exception e = assertThrows(IllegalStateException.class, () ->
                locationUpdateRules.handle(locationUpdateFacade));
        assertTrue(e.getMessage().contains("must provide location parameters"));
    }

    @Test
    void should_throw_IllegalArgumentException_when_locationId_null() {
        Location newLocation = new Location(22.1, 22.1, "base");
        LocationUpdateFacade locationUpdateFacade = new LocationUpdateFacade(newLocation);
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                locationUpdateRules.handle(locationUpdateFacade));
        assertTrue(e.getMessage().contains("must provide rebel id"));
    }

    @Test
    void should_set_latitude_to_minus_90_when_under_negative_90() {
        Location newLocation = new Location(-921.2, 2.2, "base");
        newLocation.setId(1);
        LocationUpdateFacade locationUpdateFacade = new LocationUpdateFacade(newLocation);
        locationUpdateRules.handle(locationUpdateFacade);
        assertEquals(-90, newLocation.getLatitude());
    }

    @Test
    void should_set_latitude_to_90_when_over_90() {
        Location newLocation = new Location(232.2, 2.2, "base");
        newLocation.setId(1);
        LocationUpdateFacade locationUpdateFacade = new LocationUpdateFacade(newLocation);
        locationUpdateRules.handle(locationUpdateFacade);
        assertEquals(90, newLocation.getLatitude());
    }

    @Test
    void should_set_longitude_to_minus_180_when_under_negative_180() {
        Location newLocation = new Location(2.2, -1434.2, "base");
        newLocation.setId(1);
        LocationUpdateFacade locationUpdateFacade = new LocationUpdateFacade(newLocation);
        locationUpdateRules.handle(locationUpdateFacade);
        assertEquals(-180, newLocation.getLongitude());
    }

    @Test
    void should_set_longitude_to_180_when_over_180() {
        Location newLocation = new Location(2.2, 23123.2, "base");
        newLocation.setId(1);
        LocationUpdateFacade locationUpdateFacade = new LocationUpdateFacade(newLocation);
        locationUpdateRules.handle(locationUpdateFacade);
        assertEquals(180, newLocation.getLongitude());
    }

    @Test
    void should_return_base_as_undefined_if_not_provided() {
        Location newLocation = new Location(23.2, 322.2, null);
        newLocation.setId(1);
        LocationUpdateFacade locationUpdateFacade = new LocationUpdateFacade(newLocation);
        Exception e = assertThrows(Exception.class, () ->
                locationUpdateRules.handle(locationUpdateFacade)
        );
        assertTrue(e.getMessage().contains("must provide base"));
    }

    @Test
    void should_return_30char_base_when_over_30char_provided() {
        Location newLocation = new Location(2.2, 2.2, "b".repeat(230));
        newLocation.setId(1);
        LocationUpdateFacade locationUpdateFacade = new LocationUpdateFacade(newLocation);
        locationUpdateRules.handle(locationUpdateFacade);
        assertEquals(30, newLocation.getBase().length());
    }
}