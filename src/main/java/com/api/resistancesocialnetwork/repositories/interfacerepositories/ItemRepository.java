package com.api.resistancesocialnetwork.repositories.interfacerepositories;

import com.api.resistancesocialnetwork.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    void save(Item item);

    Optional<Item> findById(Integer id);

    boolean existsById(Integer id);

    List<Item> findAll();

    void saveAll(List<Item> items);

    Optional<Item> findItemByNameAndInventoryId(Integer inventoryId, String itemName);
}
