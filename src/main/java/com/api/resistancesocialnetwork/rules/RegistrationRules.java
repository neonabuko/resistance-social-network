package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;

import java.util.ArrayList;
import java.util.List;

public class RegistrationRules {
    public void format(Rebel rebel, Location location, Inventory inventory) {
        GenericRules genericRules = new GenericRules();

        rebel.setName(genericRules.handle(rebel.getName()));
        rebel.setGender(genericRules.handle(rebel.getGender()));
        rebel.setAge(genericRules.handle(rebel.getAge()));

        location.setLatitude(genericRules.handle(location.getLatitude(), 90));
        location.setLongitude(genericRules.handle(location.getLongitude(), 180));
        location.setBase(genericRules.handle(location.getBase()));

        List<Item> fInventoryList = new ArrayList<>();

        for (Item i: inventory.getItems()) {
            fInventoryList.add(
                    new Item(
                            genericRules.handle(i.getName()),
                            genericRules.handle(i.getPrice()))
            );
        }
        inventory.setNewItemList(fInventoryList);
    }
}
