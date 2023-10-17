package com.api.resistancesocialnetwork.facade;

import com.api.resistancesocialnetwork.entity.Location;

import java.util.Optional;

public record UpdateLocationFacade(Location location) {
    public Optional<Location> getLocation() {
        return Optional.ofNullable(location);
    }
}
