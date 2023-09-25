package com.api.resistancesocialnetwork.rules;


import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FormatEntities {
    private final FormatData formatData;

    public FormatEntities(FormatData formatData) {
        this.formatData = formatData;
    }

    public void formatRebel(Rebel rebel) {
        rebel = Optional.ofNullable(rebel).orElseThrow(
                () -> new IllegalStateException("must provide rebel parameters")
        );
        rebel.setStats(
                formatData.formatString(rebel.getName(), "rebel name"),
                formatData.formatInteger(rebel.getAge(), "rebel age"),
                formatData.formatString(rebel.getGender(), "rebel gender")
        );
    }

    public void formatLocation(Location location) {
        location = Optional.ofNullable(location).orElseThrow(
                () -> new IllegalStateException("must provide location parameters")
        );

        location.setLocation(
                formatData.formatCoordinate(location.getLatitude(), 90),
                formatData.formatCoordinate(location.getLongitude(), 180),
                formatData.formatString(location.getBase(), "base")
        );
    }

    public void formatInventory(Inventory inventory) {
        inventory = Optional.ofNullable(inventory).orElseThrow(
                () -> new IllegalStateException("must provide inventory parameters")
        );

        for (Item item : inventory.getItems()) {
            Optional.ofNullable(item).orElseThrow(
                    () -> new IllegalStateException("must provide at least one item")
            );

            item.setName(formatData.formatString(item.getName(), "item name"));
            item.setPrice(formatData.formatInteger(item.getPrice(), "item price"));
        }
    }
}