package com.api.resistancesocialnetwork.facade;

import com.api.resistancesocialnetwork.entity.Location;

import java.util.Optional;

public record UpdateLocationFacade(Double latitude, Double longitude, String base) {
}
