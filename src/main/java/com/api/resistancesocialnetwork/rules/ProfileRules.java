package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.entity.Item;
import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.facade.ProfileFacade;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import com.api.resistancesocialnetwork.usecase.formatters.FormatEntities;
import org.springframework.stereotype.Component;

@Component
public class ProfileRules {
    private final FormatEntities formatEntities;

    public ProfileRules(FormatEntities formatEntities) {
        this.formatEntities = formatEntities;
    }

    public void handle(ProfileFacade profileFacade) throws ResistanceSocialNetworkException {
        assert_facade_not_null(profileFacade);
        assert_rebel_valid(profileFacade.rebel());
        assert_coordinates_valid(profileFacade.location());
        assert_inventory_valid(profileFacade.inventory());
    }

    private void assert_facade_not_null(ProfileFacade profileFacade) throws ResistanceSocialNetworkException {
        if (profileFacade == null) throw new ResistanceSocialNetworkException("must provide signup parameters");
    }

    private void assert_rebel_valid(Rebel rebel) throws ResistanceSocialNetworkException {
        formatEntities.formatRebel(rebel);
    }

    private void assert_coordinates_valid(Location location) throws ResistanceSocialNetworkException {
        formatEntities.formatLocation(location);
    }

    private void assert_inventory_valid(Inventory inventory) throws ResistanceSocialNetworkException {
        if (inventory.getItems().isEmpty()) throw new ResistanceSocialNetworkException("must provide at least one item");
        for (Item item : inventory.getItems()) {
            if (item == null) throw new ResistanceSocialNetworkException("must provide item parameters");
        }
        formatEntities.formatInventory(inventory);
    }
}
