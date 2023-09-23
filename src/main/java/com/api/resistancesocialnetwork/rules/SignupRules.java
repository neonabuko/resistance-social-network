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

    public void format(Rebel rebel, Location location, Inventory inventory) {
        DataFormatRules dataFormatRules = new DataFormatRules();

        dataFormatRules.handle(Optional.ofNullable(rebel).orElseThrow(
                () -> new InvalidDataAccessApiUsageException("must provide rebel parameters")
        ));

        dataFormatRules.handle(Optional.ofNullable(location).orElseThrow(
                () -> new InvalidDataAccessApiUsageException("must provide location parameters")
        ));

        for (Item newItem : Optional.ofNullable(inventory).orElseThrow(
                () -> new InvalidDataAccessApiUsageException("must provide inventory parameters")).getItems()) {
            newItem.setName(dataFormatRules.handle(newItem.getName()));
            newItem.setPrice(dataFormatRules.handle(newItem.getPrice()));
        }
    }
}
