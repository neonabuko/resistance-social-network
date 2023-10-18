package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.entity.Item;
import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.facade.ProfileFacade;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import com.api.resistancesocialnetwork.usecase.formatters.FormatData;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfileRules {
    private final FormatData formatData;

    public ProfileRules(FormatData formatData) {
        this.formatData = formatData;
    }

    public void handle(Rebel rebel, Location location, Inventory inventory) throws ResistanceException {
        assert_rebel_valid(rebel.getName(), rebel.getAge(), rebel.getGender());
        assert_coordinates_valid(location.getLatitude(), location.getLongitude(), location.getBase());
        assert_inventory_valid(inventory.getItems());
    }

    private void assert_rebel_valid(String name, Integer age, String gender) throws ResistanceException {
        formatData.formatString(name, 30, "rebel name");
        formatData.formatInteger(age, 50, "rebel age");
        formatData.formatString(gender, 20, "rebel gender");
    }

    private void assert_coordinates_valid(Double latitude, Double longitude, String base) throws ResistanceException {
        formatData.formatCoordinate(latitude, 180);
        formatData.formatCoordinate(longitude, 180);
        formatData.formatString(base, 20, "base");
    }

    private void assert_inventory_valid(List<Item> items) throws ResistanceException {
        if (items.isEmpty()) throw new ResistanceException("must provide at least one item");

        for (Item item : items) {
            if (item == null) throw new ResistanceException("must provide item parameters");
            item.setName(formatData.formatString(item.getName(), 20, "item name"));
            item.setPrice(formatData.formatInteger(item.getPrice(), 4, "item price"));
        }
    }
}
