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
        check_If_Rebel_Is_Null(rebel);
        check_If_Location_Is_Null(location);
        check_If_Inventory_Is_Null(inventory);
    }

    private void check_If_Rebel_Is_Null(Rebel rebel){
        dataFormatRules.handle(Optional.ofNullable(rebel).orElseThrow(
                () -> new InvalidDataAccessApiUsageException("must provide rebel parameters")
        ));
    }

    private void check_If_Location_Is_Null(Location location){
        dataFormatRules.handleWithException(location);
    }

    private void check_If_Inventory_Is_Null(Inventory inventory){
        for (Item newItem : Optional.ofNullable(inventory).orElseThrow(
                () -> new InvalidDataAccessApiUsageException("must provide inventory parameters")).getItems()) {
            newItem.setName(dataFormatRules.handle(newItem.getName()));
            newItem.setPrice(dataFormatRules.handle(newItem.getPrice()));
        }
    }
}
