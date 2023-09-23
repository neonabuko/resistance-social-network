package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import org.springframework.stereotype.Service;

@Service
public class SignupRules {

    public void format(Rebel rebel, Location location, Inventory inventory) {
        DataFormatRules dataFormatRules = new DataFormatRules();

        dataFormatRules.handle(rebel);

        dataFormatRules.handle(location);

        for (Item newItem : inventory.getItems()) {
            newItem.setName(dataFormatRules.handle(newItem.getName()));
            newItem.setPrice(dataFormatRules.handle(newItem.getPrice()));
        }
    }
}
