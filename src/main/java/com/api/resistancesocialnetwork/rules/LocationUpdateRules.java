package com.api.resistancesocialnetwork.rules;


import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class LocationUpdateRules {
    private final LocationRepository locationRepo;
    private final DataFormatRules dataFormatRules = new DataFormatRules();

    @Autowired
    public LocationUpdateRules(LocationRepository locationRepo) {
        this.locationRepo = locationRepo;
    }

    public Location handle(Integer locationId, Double latitude, Double longitude, String base) throws NoSuchElementException {
        Optional<Integer> optionalLocationId = Optional.ofNullable(locationId);

        Location location = locationRepo.findById(optionalLocationId.orElseThrow(
                () -> new NoSuchElementException("must provide location id")
        )).orElseThrow(
                () -> new NoSuchElementException("location not found")
        );

        location.setNewLocation(
                dataFormatRules.handle(latitude, 90),
                dataFormatRules.handle(longitude, 180),
                dataFormatRules.handle(base)
        );
        return location;
    }
}
