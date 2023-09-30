package com.api.resistancesocialnetwork.request.facade;

import com.api.resistancesocialnetwork.model.Location;

import java.util.Optional;

public record LocationUpdateFacade(Location location) {

    public Optional<Location> getLocation() {
        return Optional.ofNullable(location);
    }
}