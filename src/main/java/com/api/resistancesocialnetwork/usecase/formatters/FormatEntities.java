package com.api.resistancesocialnetwork.usecase.formatters;


import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.entity.Item;
import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import org.springframework.stereotype.Component;

@Component
public class FormatEntities {
    private final FormatData formatData;

    public FormatEntities(FormatData formatData) {
        this.formatData = formatData;
    }

    public void formatRebel(Rebel rebel) throws ResistanceException {
        rebel.setStats(
                formatData.formatString(rebel.getName(), 30, "rebel name"),
                formatData.formatInteger(rebel.getAge(), 50, "rebel age"),
                formatData.formatString(rebel.getGender(), 10, "rebel gender")
        );
    }

    public void formatLocation(Location location) throws ResistanceException {
        location.setLocation(
                formatData.formatCoordinate(location.getLatitude(), 90),
                formatData.formatCoordinate(location.getLongitude(), 180),
                formatData.formatString(location.getBase(), 30, "base")
        );
    }

    public void formatInventory(Inventory inventory) throws ResistanceException {
        for (Item item : inventory.getItems()) {
            item.setName(formatData.formatString(item.getName(), 20, "item name"));
            item.setPrice(formatData.formatInteger(item.getPrice(), 4, "item price"));
        }
    }
}