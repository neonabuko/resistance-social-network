package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.entity.Item;
import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.facade.ProfileFacade;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import com.api.resistancesocialnetwork.usecase.formatters.FormatEntities;
import org.springframework.stereotype.Component;

@Component
public class ProfileRules {
    private final FormatEntities formatEntities;

    public ProfileRules(FormatEntities formatEntities) {
        this.formatEntities = formatEntities;
    }

    public void handle(ProfileFacade profileFacade) throws ResistanceException {
        assert_facade_not_null(profileFacade);
        assert_rebel_valid(profileFacade.rebel());
        assert_coordinates_valid(profileFacade.location());
        assert_inventory_valid(profileFacade.inventory());
    }

    private void assert_facade_not_null(ProfileFacade profileFacade) throws ResistanceException {
        if (profileFacade == null) throw new ResistanceException("must provide signup parameters");
    }

    private void assert_rebel_valid(Rebel rebel) throws ResistanceException {
        formatEntities.formatRebel(rebel);
    }

    private void assert_coordinates_valid(Location location) throws ResistanceException {
        formatEntities.formatLocation(location);
    }

    private void assert_inventory_valid(Inventory inventory) throws ResistanceException {
        if (inventory.getItems().isEmpty()) throw new ResistanceException("must provide at least one item");
        for (Item item : inventory.getItems()) {
            if (item == null) throw new ResistanceException("must provide item parameters");
        }
        formatEntities.formatInventory(inventory);
    }
}
