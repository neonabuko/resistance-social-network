package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.repositories.LocationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LocationUpdateRulesTest {
    @Autowired
    private LocationRepository locationRepo;
    @Autowired
    private LocationUpdateRules locationUpdateRules;
    private final Location expectedLocation = new Location(42.1, 22.5, "base");

    @Test
    void should_throw_NoSuchElementException_when_locationId_not_provided() {
        Exception e = assertThrows(NoSuchElementException.class, () ->
                locationUpdateRules.handle(null, 16.2, 52.25, "new base")
        );
        assertTrue(e.getMessage().contains("must provide location id"));
    }
    @Test
    void should_throw_NoSuchElementException_when_location_not_found() {
        Exception e = assertThrows(NoSuchElementException.class, () ->
                locationUpdateRules.handle(45, 16.2, 52.25, "new base")
        );
        assertTrue(e.getMessage().contains("location not found"));
    }

    @Test
    void should_throw_IllegalStateException_when_latitude_not_filled() {
        locationRepo.save(expectedLocation);
        Exception e = assertThrows(IllegalStateException.class, () ->
                locationUpdateRules.handle(1, null, 32.1, "base")
        );
        assertTrue(e.getMessage().contains("all parameters required"));
    }

    @Test
    void should_throw_IllegalStateException_when_longitude_not_filled() {
        locationRepo.save(expectedLocation);
        Exception e = assertThrows(IllegalStateException.class, () ->
                locationUpdateRules.handle(1, 23.1, null, "base")
        );
        assertTrue(e.getMessage().contains("all parameters required"));
    }

    @Test
    void should_save_new_location() {
        Location joaoLocation = locationRepo.save(expectedLocation);
        Location actualLocation = locationRepo.findById(joaoLocation.getId()).get();
        assertEquals(expectedLocation.toString(), actualLocation.toString());
    }
}