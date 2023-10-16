package com.api.resistancesocialnetwork.repository.repositoryinterfaces;

import com.api.resistancesocialnetwork.entity.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository {
    void save(Inventory inventory);
    void saveAll(List<Inventory> inventories);

    Optional<Inventory> findById(Integer id);

    boolean existsById(Integer id);

    List<Inventory> findAll();
}
