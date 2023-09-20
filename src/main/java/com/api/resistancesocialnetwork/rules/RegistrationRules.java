package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RegistrationRules {

    public List<?> format(Rebel rebel, Location location, Inventory inventory) {
        DataFormatRules dataFormatRules = new DataFormatRules();

        Rebel formattedRebel = dataFormatRules.handle(rebel);

        Location formattedLocation = dataFormatRules.handle(location);

        List<Item> fInventoryList = new ArrayList<>();
        for (Item newItem: inventory.getItems()) {
            newItem.setName(dataFormatRules.handle(newItem.getName()));
            newItem.setPrice(dataFormatRules.handle(newItem.getPrice()));

            fInventoryList.add(newItem);
            newItem.setInventory(inventory);
        }

        inventory.setItems(fInventoryList);

        location.setRebel(formattedRebel);
        inventory.setRebel(formattedRebel);

        return new ArrayList<>(Arrays.asList(formattedRebel, formattedLocation, inventory));
    }
}
