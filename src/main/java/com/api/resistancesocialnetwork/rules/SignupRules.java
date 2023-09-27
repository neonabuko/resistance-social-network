package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.formatters.FormatEntities;
import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.request.facade.SignupFacade;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SignupRules {
    private final FormatEntities formatEntities;

    public SignupRules(FormatEntities formatEntities) {
        this.formatEntities = formatEntities;
    }

    public void handle(SignupFacade signupFacade) throws ResistanceSocialNetworkException {
        assert_reportFacade_provided(signupFacade);
        assert_rebel_valid(signupFacade.rebel());
        assert_coordinates_valid(signupFacade.location());
        assert_inventory_valid(signupFacade.inventory());
    }

    private void assert_reportFacade_provided(SignupFacade signupFacade) throws ResistanceSocialNetworkException {
        if (signupFacade == null) throw new ResistanceSocialNetworkException("must provide signup parameters");
    }
    private void assert_rebel_valid(Rebel rebel) throws ResistanceSocialNetworkException {
        if (rebel == null) throw new ResistanceSocialNetworkException("must provide rebel parameters");
        formatEntities.formatRebel(rebel);
    }

    private void assert_coordinates_valid(Location location) throws ResistanceSocialNetworkException {
        Optional.ofNullable(location).orElseThrow(
                () -> new ResistanceSocialNetworkException("must provide location parameters")
        );
        formatEntities.formatLocation(location);
    }

    private void assert_inventory_valid(Inventory inventory) throws ResistanceSocialNetworkException {
        Optional.ofNullable(inventory).orElseThrow(
                () -> new ResistanceSocialNetworkException("must provide inventory parameters")
        );
        for (Item item : inventory.getItems()) {
            Optional.ofNullable(item).orElseThrow(
                    () -> new ResistanceSocialNetworkException("must provide item parameters")
            );
        }
        formatEntities.formatInventory(inventory);
    }
}
