package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.repositories.LocationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LocationUpdateUseCaseTest {

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private LocationUpdateUseCase locationUpdateUseCase;

    @Test
    void should_save_new_location(){
        Location oldLocation = new Location(150.1,12.2,"xereca");
        locationRepository.save(oldLocation);

        Double newLatitude = 38.1;
        Double newLongitude = 64.7;
        String newBase = "jumanji";

        locationUpdateUseCase.handle(oldLocation.getId(), newLatitude, newLongitude, newBase);
        Location newLocation = locationRepository.findById(oldLocation.getId()).get();

        assertEquals(newLatitude, newLocation.getLatitude());
        assertEquals(newLongitude, newLocation.getLongitude());
        assertEquals(newBase, newLocation.getBase());
    }
}
