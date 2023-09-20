package com.api.resistancesocialnetwork.repositories;


import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    default Optional<Item> findItemByName(Integer id, String itemName) {
        if (findById(id).isPresent())
            return findById(id).get().getItems().stream()
                .filter(item -> item.getName().equals(itemName))
                .findFirst();
        else return Optional.empty();
    }
}