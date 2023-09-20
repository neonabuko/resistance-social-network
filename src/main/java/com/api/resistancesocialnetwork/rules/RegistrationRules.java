package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationRules {
    @Transactional
    public void format(Rebel rebel, Location location, Inventory inventory) {
        GenericRules genericRules = new GenericRules();

        rebel.setName(genericRules.handle(rebel.getName()));
        rebel.setGender(genericRules.handle(rebel.getGender()));
        rebel.setAge(genericRules.handle(rebel.getAge()));
        rebel.setReportCounter(0);

        location.setLatitude(genericRules.handle(location.getLatitude(), 90));
        location.setLongitude(genericRules.handle(location.getLongitude(), 180));
        location.setBase(genericRules.handle(location.getBase()));

        location.setRebel(rebel);

        List<Item> fInventoryList = new ArrayList<>();

        for (Item newItem: inventory.getItems()) {
            newItem.setName(genericRules.handle(newItem.getName()));
            newItem.setPrice(genericRules.handle(newItem.getPrice()));

            fInventoryList.add(newItem);
            newItem.setInventory(inventory);
        }

        inventory.setItems(fInventoryList);

        inventory.setRebel(rebel);
    }
}
