package com.api.resistancesocialnetwork.repositories.interfacerepositories;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository {
    void save(Inventory inventory);

    Optional<Inventory> findById(Integer id);

    boolean existsById(Integer id);

    List<Inventory> findAll();

    Optional<Item> findItemByName(Integer id, String itemName);
}
