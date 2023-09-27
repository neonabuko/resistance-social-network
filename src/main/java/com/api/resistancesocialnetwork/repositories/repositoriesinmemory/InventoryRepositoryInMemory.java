package com.api.resistancesocialnetwork.repositories.repositoriesinmemory;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.InventoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InventoryRepositoryInMemory implements InventoryRepository {
    private final List<Inventory> inventoryList;

    public InventoryRepositoryInMemory() {
        inventoryList = new ArrayList<>();
    }
    @Override
    public void save(Inventory inventory) {
        inventoryList.add(inventory);
    }

    @Override
    public void saveAll(List<Inventory> inventories) {
        inventoryList.addAll(inventories);
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
}
