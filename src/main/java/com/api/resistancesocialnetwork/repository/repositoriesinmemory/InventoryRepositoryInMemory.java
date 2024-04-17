package com.api.resistancesocialnetwork.repository.repositoriesinmemory;

import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.InventoryRepository;

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
    public Optional<Inventory> findBy(Integer id) {
        return inventoryList.stream().filter(inventory -> inventory.getId().equals(id)).findFirst();
    }

    @Override
    public boolean existsBy(Integer id) {
        return inventoryList.stream().anyMatch(inventory -> inventory.getId().equals(id));
    }

    @Override
    public List<Inventory> findAll() {
        return inventoryList;
    }
}
