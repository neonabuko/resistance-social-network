package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.entity.Item;
import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.request.facade.SignupFacade;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import com.api.resistancesocialnetwork.usecase.formatters.FormatEntities;
import org.springframework.stereotype.Component;

@Component
public class SignupRules {
    private final FormatEntities formatEntities;

    public SignupRules(FormatEntities formatEntities) {
        this.formatEntities = formatEntities;
    }

    public void handle(SignupFacade signupFacade) throws ResistanceSocialNetworkException {
        assert_facade_not_null(signupFacade);
        assert_rebel_valid(signupFacade.rebel());
        assert_coordinates_valid(signupFacade.location());
        assert_inventory_valid(signupFacade.inventory());
    }

    private void assert_facade_not_null(SignupFacade signupFacade) throws ResistanceSocialNetworkException {
        if (signupFacade == null) throw new ResistanceSocialNetworkException("must provide signup parameters");
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
