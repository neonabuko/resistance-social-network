package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.LocationRepositoryInMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LocationUpdateRulesTest {
    private final LocationRepositoryInMemory locationRepoInMem = new LocationRepositoryInMemory();
    private final LocationUpdateRules locationUpdateRules = new LocationUpdateRules(locationRepoInMem);
    private final Location oldLocation = new Location(42.1, 22.5, "base");

    @BeforeEach
    void setUp() {
        oldLocation.setId(1);
        locationRepoInMem.saveInMem(oldLocation);
    }

    @Test
    void should_throw_NoSuchElementException_when_locationId_not_provided() {
        Exception e = assertThrows(NoSuchElementException.class, () -> locationUpdateRules.handle(null, 16.2, 52.25, "new base"));
        assertTrue(e.getMessage().contains("must provide location id"));
    }

    @Test
    void should_throw_NoSuchElementException_when_location_not_found() {
        Exception e = assertThrows(NoSuchElementException.class, () -> locationUpdateRules.handle(45, 16.2, 52.25, "new base"));
        assertTrue(e.getMessage().contains("location not found"));
    }

    @Test
    void should_throw_IllegalStateException_when_latitude_not_provided() {
        Exception e = assertThrows(IllegalStateException.class, () -> locationUpdateRules.handle(oldLocation.getId(), null, 32.1, "base"));
        assertTrue(e.getMessage().contains("coordinates required for location"));
    }

    @Test
    void should_throw_IllegalStateException_when_longitude_not_provided() {
        Exception e = assertThrows(IllegalStateException.class, () -> locationUpdateRules.handle(oldLocation.getId(), 23.1, null, "base"));
        assertTrue(e.getMessage().contains("coordinates required for location"));
    }

    @Test
    void should_set_latitude_to_minus_90_when_under_negative_90() {
        Location newLocation = locationUpdateRules.handle(oldLocation.getId(), -9121.2, 11.1, "base");
        locationRepoInMem.saveInMem(newLocation);
        assertEquals(-90, locationRepoInMem.findById(newLocation.getId()).get().getLatitude());
    }

    @Test
    void should_set_latitude_to_90_when_over_90() {
        Location newLocation = locationUpdateRules.handle(oldLocation.getId(), 293402349.213, 11.1, "base");
        locationRepoInMem.saveInMem(newLocation);
        assertEquals(90, locationRepoInMem.findById(newLocation.getId()).get().getLatitude());
    }

    @Test
    void should_set_longitude_to_minus_180_when_under_negative_180() {
        Location newLocation = locationUpdateRules.handle(oldLocation.getId(), 18.2, -124331.23, "base");
        locationRepoInMem.saveInMem(newLocation);
        assertEquals(-180, locationRepoInMem.findById(newLocation.getId()).get().getLongitude());
    }

    @Test
    void should_set_longitude_to_180_when_over_180() {
        Location newLocation = locationUpdateRules.handle(oldLocation.getId(), 24.2, 12378.23, "base");
        locationRepoInMem.saveInMem(newLocation);
        assertEquals(180, locationRepoInMem.findById(newLocation.getId()).get().getLongitude());
    }

    @Test
    void should_return_base_as_undefined_if_not_provided() {
        Location newLocation = locationUpdateRules.handle(oldLocation.getId(), 24.2, 3.2, null);
        locationRepoInMem.saveInMem(newLocation);
        assertEquals("undefined", locationRepoInMem.findById(newLocation.getId()).get().getBase());
    }

    @Test
    void should_return_30char_base_when_over_30char_provided() {
        Location newLocation = locationUpdateRules.handle(oldLocation.getId(), 24.2, 3.2, "a".repeat(31));
        locationRepoInMem.saveInMem(newLocation);
        assertEquals(30, locationRepoInMem.findById(newLocation.getId()).get().getBase().length());
    }
}