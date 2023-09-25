package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignupRules {

    DataFormatRules dataFormatRules = new DataFormatRules();

    public void handle(Rebel rebel, Location location, Inventory inventory) {
        assert_rebel_not_null(rebel);
        assert_coordinates_are_valid(location);
        assert_inventory_is_valid(inventory);
    }

    private void assert_rebel_not_null(Rebel rebel){
        dataFormatRules.handle(Optional.ofNullable(rebel).orElseThrow(
                () -> new InvalidDataAccessApiUsageException("must provide rebel parameters")
        ));
    }

    private void assert_coordinates_are_valid(Location location){
        dataFormatRules.handleWithException(location);
    }

    private void assert_inventory_is_valid(Inventory inventory){
        for (Item newItem : Optional.ofNullable(inventory).orElseThrow(
                () -> new InvalidDataAccessApiUsageException("must provide inventory parameters")).getItems()) {
            newItem.setName(dataFormatRules.handle(newItem.getName()));
            newItem.setPrice(dataFormatRules.handle(newItem.getPrice()));
        }
    }
}
