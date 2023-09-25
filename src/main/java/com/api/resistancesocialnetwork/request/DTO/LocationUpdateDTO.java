package com.api.resistancesocialnetwork.request.DTO;

import java.util.Optional;

public class LocationUpdateDTO {

    private final Integer locationId;
    private final Double latitude;
    private final Double longitude;
    private final String base;

    public LocationUpdateDTO(Integer locationId, Double latitude, Double longitude, String base) {
        this.locationId = locationId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.base = base;
    }

    public Integer getLocationId() {
        return Optional.ofNullable(locationId).orElse(0);
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getBase() {
        return base;
    }
}
