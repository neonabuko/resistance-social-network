package com.api.resistancesocialnetwork.rules;


import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationUpdateRules {
    private final LocationRepository locationRepo;
    private final GenericRules genericRules = new GenericRules();

    @Autowired
    public LocationUpdateRules(LocationRepository locationRepo) {
        this.locationRepo = locationRepo;
    }

    public Optional<Location> handle(Integer locationId, Double latitude, Double longitude, String base) {
        Optional<Location> oldLocation = locationRepo.findById(locationId);

        if (oldLocation.isEmpty()) return Optional.empty();

        oldLocation.get().setNewLocation(
                genericRules.handle(latitude, 90),
                genericRules.handle(longitude, 180),
                genericRules.handle(base)
        );

        return oldLocation;
    }
}
