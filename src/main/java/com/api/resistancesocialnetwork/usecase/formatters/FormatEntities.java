package com.api.resistancesocialnetwork.usecase.formatters;


import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import org.springframework.stereotype.Component;

@Component
public class FormatEntities {
    private final FormatData formatData;

    public FormatEntities(FormatData formatData) {
        this.formatData = formatData;
    }

    public void formatRebel(Rebel rebel) throws ResistanceSocialNetworkException {
        rebel.setStats(
                formatData.formatString(rebel.getName(), 30, "rebel name"),
                formatData.formatInteger(rebel.getAge(), 50, "rebel age"),
                formatData.formatString(rebel.getGender(), 10, "rebel gender")
        );
    }

    public void formatLocation(Location location) throws ResistanceSocialNetworkException {
        location.setLocation(
                formatData.formatCoordinate(location.getLatitude(), 90),
                formatData.formatCoordinate(location.getLongitude(), 180),
                formatData.formatString(location.getBase(), 30, "base")
        );
    }

    public void formatInventory(Inventory inventory) throws ResistanceSocialNetworkException {
        for (Item item : inventory.getItems()) {
            item.setName(formatData.formatString(item.getName(), 20, "item name"));
            item.setPrice(formatData.formatInteger(item.getPrice(), 4, "item price"));
        }
    }
}