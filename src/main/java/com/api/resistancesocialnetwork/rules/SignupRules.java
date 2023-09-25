package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import org.springframework.stereotype.Component;

@Component
public class SignupRules {

    private final FormatEntities formatEntities;

    public SignupRules(FormatEntities formatEntities) {
        this.formatEntities = formatEntities;
    }

    public void handle(Rebel rebel, Location location, Inventory inventory) {
        assert_rebel_valid(rebel);
        assert_coordinates_valid(location);
        assert_inventory_valid(inventory);
    }

    private void assert_rebel_valid(Rebel rebel){
        formatEntities.formatRebel(rebel);
    }

    private void assert_coordinates_valid(Location location){
        formatEntities.formatLocation(location);
    }

    private void assert_inventory_valid(Inventory inventory){
        formatEntities.formatItems(inventory);
    }
}
