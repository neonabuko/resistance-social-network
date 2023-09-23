package com.api.resistancesocialnetwork.repositories.repositoriesinmemory;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.InventoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InventoryRepositoryInMemory implements InventoryRepository {
    private final List<Inventory> inventoryList = new ArrayList<>();

    public void saveInMem(Inventory inventory) {
        inventoryList.add(inventory);
    }

    @Override
    public void save(Inventory inventory) {
        inventoryList.add(inventory);
    }

    public void deleteAllInMem() {
        inventoryList.clear();
    }

    @Override
    public Optional<Inventory> findById(Integer id) {
        return inventoryList.stream().filter(inventory -> inventory.getId().equals(id)).findFirst();
    }

    @Override
    public boolean existsById(Integer id) {
        return inventoryList.stream().anyMatch(inventory -> inventory.getId().equals(id));
    }

    @Override
    public List<Inventory> findAll() {
        return inventoryList;
    }

    @Override
    public Optional<Item> findItemByName(Integer id, String itemName) {
        return findById(id).get().getItems().stream().filter(item -> item.getName().equals(itemName)).findFirst();
    }
}
