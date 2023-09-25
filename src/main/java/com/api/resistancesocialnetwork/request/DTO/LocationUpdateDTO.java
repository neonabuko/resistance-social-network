package com.api.resistancesocialnetwork.request.DTO;

import com.api.resistancesocialnetwork.model.Location;

public class LocationUpdateDTO {

    private final Location location;

    public LocationUpdateDTO(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
