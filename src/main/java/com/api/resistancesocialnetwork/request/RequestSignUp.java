package com.api.resistancesocialnetwork.request;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;

public record RequestSignUp(Rebel rebel, Location location, Inventory inventory) {
    @Override
    public String toString() {
        return rebel + "\n" + location + "\n" + inventory;
    }
}
