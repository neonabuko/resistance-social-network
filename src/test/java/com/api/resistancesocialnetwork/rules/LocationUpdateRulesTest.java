package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.repositories.LocationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LocationUpdateRulesTest {
    @Autowired
    private LocationRepository locationRepo;
    @Autowired
    private LocationUpdateRules locationUpdateRules;

//    @Test
//    void should_do_nothing_when_rebel_not_found() {
//        locationUpdateRules.handle(45, probeLocation);
//        assertFalse(locationRepo.findAll().contains(probeLocation));
//    }

    @Test
    void should_save_new_location() {
        Location expectedLocation = new Location(42.1, 22.5, "base");
        Location joaoLocation = locationRepo.save(expectedLocation);
        Location actualLocation = locationRepo.findById(joaoLocation.getId()).get();
        assertEquals(expectedLocation.toString(), actualLocation.toString());
    }
}