package com.api.resistancesocialnetwork.request.DTO;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;

public record SignupDTO(Rebel rebel, Location location, Inventory inventory) {
}
