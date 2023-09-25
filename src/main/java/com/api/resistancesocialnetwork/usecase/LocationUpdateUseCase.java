package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.LocationRepository;
import com.api.resistancesocialnetwork.request.DTO.LocationUpdateDTO;
import com.api.resistancesocialnetwork.rules.LocationUpdateRules;
import org.springframework.stereotype.Service;

@Service
public class LocationUpdateUseCase {
    private final LocationRepository locationRepository;
    private final LocationUpdateRules locationUpdateRules;

    public LocationUpdateUseCase(LocationRepository locationRepository, LocationUpdateRules locationUpdateRules) {
        this.locationRepository = locationRepository;
        this.locationUpdateRules = locationUpdateRules;
    }

    public void handle(LocationUpdateDTO locationUpdateDTO) {
        Location location = locationRepository.findById(locationUpdateDTO.getLocation().getId()).orElseThrow(
                () -> new IllegalArgumentException("location not found")
        );
        locationUpdateRules.formatLocationParams(locationUpdateDTO.getLocation());

        location.setLocation(
                locationUpdateDTO.getLocation().getLatitude(),
                locationUpdateDTO.getLocation().getLongitude(),
                locationUpdateDTO.getLocation().getBase()
        );
    }
}