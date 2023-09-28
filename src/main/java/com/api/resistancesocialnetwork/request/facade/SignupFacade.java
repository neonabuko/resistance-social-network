package com.api.resistancesocialnetwork.request.facade;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;

import java.util.Optional;

public record SignupFacade(Rebel rebel, Location location, Inventory inventory) {
    @Override
    public Rebel rebel() {
        return Optional.ofNullable(rebel).orElse(new Rebel());
    }
    @Override
    public Location location() {
        return Optional.ofNullable(location).orElse(new Location());
    }
    @Override
    public Inventory inventory() {
        return Optional.ofNullable(inventory).orElse(new Inventory());
    }
}
