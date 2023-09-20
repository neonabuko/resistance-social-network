package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.repositories.LocationRepository;
import com.api.resistancesocialnetwork.rules.LocationUpdateRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationUpdateUseCase {
    private final LocationRepository locationRepository;
    private final LocationUpdateRules locationUpdateRules;

    @Autowired
    public LocationUpdateUseCase(LocationRepository locationRepository, LocationUpdateRules locationUpdateRules) {
        this.locationRepository = locationRepository;
        this.locationUpdateRules = locationUpdateRules;
    }

    public void handle(Integer locationId, Double latitude, Double longitude, String base) {
        Location formattedLocation = locationUpdateRules.handle(locationId, latitude, longitude, base);

        locationRepository.save(formattedLocation);
    }
}
