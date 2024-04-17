package com.api.resistancesocialnetwork.repository.repositoryinterfaces;

import com.api.resistancesocialnetwork.entity.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository {
    void save(Inventory inventory);
    void saveAll(List<Inventory> inventories);

    Optional<Inventory> findBy(Integer id);

    boolean existsBy(Integer id);

    List<Inventory> findAll();
}
