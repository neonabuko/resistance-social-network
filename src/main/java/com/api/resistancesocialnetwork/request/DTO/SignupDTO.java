package com.api.resistancesocialnetwork.request.DTO;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;

public class SignupDTO {

    private Rebel rebel;
    private Location location;
    private Inventory inventory;

    public SignupDTO(Rebel rebel, Location location, Inventory inventory) {
        this.rebel = rebel;
        this.location = location;
        this.inventory = inventory;
    }

    public Rebel getRebel() {
        return rebel;
    }

    public Location getLocation() {
        return location;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setRebel(Rebel rebel) {
        this.rebel = rebel;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
