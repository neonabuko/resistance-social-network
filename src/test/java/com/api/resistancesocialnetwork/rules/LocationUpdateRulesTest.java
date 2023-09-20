package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.repositories.LocationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class LocationUpdateRulesTest {
    @Autowired
    private LocationRepository locationRepo;
    @Autowired
    private LocationUpdateRules locationUpdateRules;
    private final Location oldLocation = new Location(42.1, 22.5, "base");

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
        locationRepo.save(oldLocation);
        Exception e = assertThrows(IllegalStateException.class, () ->
                locationUpdateRules.handle(1, null, 32.1, "base")
        );
        assertTrue(e.getMessage().contains("all parameters required"));
    }

    @Test
    void should_throw_IllegalStateException_when_longitude_not_filled() {
        locationRepo.save(oldLocation);
        Exception e = assertThrows(IllegalStateException.class, () ->
                locationUpdateRules.handle(1, 23.1, null, "base")
        );
        assertTrue(e.getMessage().contains("all parameters required"));
    }

    @Test
    void should_set_latitude_to_minus_90_when_less_than_minus_90() {
        locationRepo.save(oldLocation);
        Location newLocation = locationUpdateRules.handle(1, -9121.2, 11.1, "base");
        locationRepo.save(newLocation);
        assertEquals(-90, locationRepo.findById(newLocation.getId()).get().getLatitude());
    }

    @Test
    void should_set_latitude_to_90_when_over_90() {
        locationRepo.save(oldLocation);
        Location newLocation = locationUpdateRules.handle(1, 293402349.213, 11.1, "base");
        locationRepo.save(newLocation);
        assertEquals(90, locationRepo.findById(newLocation.getId()).get().getLatitude());
    }

    @Test
    void should_set_longitude_to_minus_180_when_less_than_minus_180() {
        locationRepo.save(oldLocation);
        Location newLocation = locationUpdateRules.handle(1, 18.2, -124331.23, "base");
        locationRepo.save(newLocation);
        assertEquals(-180, locationRepo.findById(newLocation.getId()).get().getLongitude());
    }

    @Test
    void should_set_longitude_to_180_when_over_180() {
        locationRepo.save(oldLocation);
        Location newLocation = locationUpdateRules.handle(1, 24.2, 12378.23, "base");
        locationRepo.save(newLocation);
        assertEquals(180, locationRepo.findById(newLocation.getId()).get().getLongitude());
    }

    @Test
    void should_return_base_as_undefined_if_not_provided() {
        locationRepo.save(oldLocation);
        Location newLocation = locationUpdateRules.handle(1, 24.2, 3.2, null);
        locationRepo.save(newLocation);
        assertEquals("undefined", locationRepo.findById(newLocation.getId()).get().getBase());
    }

    @Test
    void should_return_30char_base_when_over_30char() {
        locationRepo.save(oldLocation);
        Location newLocation = locationUpdateRules.handle(1, 24.2, 3.2, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        locationRepo.save(newLocation);
        assertEquals(30, locationRepo.findById(newLocation.getId()).get().getBase().length());
    }
}