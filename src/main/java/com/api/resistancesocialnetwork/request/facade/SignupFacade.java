package com.api.resistancesocialnetwork.request.facade;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;

public record SignupFacade(Rebel rebel, Location location, Inventory inventory) {
}
