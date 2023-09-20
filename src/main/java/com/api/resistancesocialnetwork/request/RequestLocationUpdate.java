package com.api.resistancesocialnetwork.request;

import com.api.resistancesocialnetwork.model.Location;

public record RequestLocationUpdate(String rebelName, Location newLocation) {
}
