package com.api.resistancesocialnetwork.rules;


import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;

import java.util.Optional;

public class DataFormatRules {
    public String formatString(String string) {
        return Optional.ofNullable(string)
                .map(s -> s.substring(0, Math.min(s.length(), 30)))
                .filter(s -> !s.isEmpty()).orElse("undefined");
    }

    public String formatBase(String base) {
        return Optional.ofNullable(base).map(s -> s.substring(0, Math.min(s.length(), 30))).orElseThrow(
                () -> new IllegalStateException("must provide base")
        );
    }

    public Double formatCoordinate(Double coord, int bound) {
        return Optional.ofNullable(coord).map(d -> Math.min(d, bound)).map(d -> Math.max(d, -bound)).orElseThrow(
                () -> new IllegalStateException("coordinates required for location")
        );
    }

    public Integer formatInteger(Integer integer) {
        int output = Math.min(Optional.ofNullable(integer).orElse(0), 100);
        return Math.max(output, 0);
    }

    public void formatRebel(Rebel rebel) {
        rebel = Optional.ofNullable(rebel).orElseThrow(
                () -> new IllegalStateException("must provide rebel parameters")
        );
        rebel.setStats(
                formatString(rebel.getName()),
                formatInteger(rebel.getAge()),
                formatString(rebel.getGender())
        );
    }

    public void formatLocation(Location location) {
        location = Optional.ofNullable(location).orElseThrow(
                () -> new IllegalStateException("must provide location parameters")
        );

        location.setLocation(
                formatCoordinate(location.getLatitude(), 90),
                formatCoordinate(location.getLongitude(), 180),
                formatBase(location.getBase())
        );
    }

    public void formatItems(Inventory inventory) {
        inventory = Optional.ofNullable(inventory).orElseThrow(
                () -> new IllegalStateException("must provide inventory parameters")
        );

        for (Item item : inventory.getItems()) {
            item.setName(formatString(item.getName()));
            item.setPrice(formatInteger(item.getPrice()));
        }
    }
}