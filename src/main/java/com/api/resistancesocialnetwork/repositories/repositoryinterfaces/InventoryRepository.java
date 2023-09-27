package com.api.resistancesocialnetwork.repositories.repositoryinterfaces;

import com.api.resistancesocialnetwork.model.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository {
    void save(Inventory inventory);
    void saveAll(List<Inventory> inventories);

    Optional<Inventory> findById(Integer id);

    boolean existsById(Integer id);

    List<Inventory> findAll();
}
