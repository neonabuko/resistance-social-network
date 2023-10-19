package com.api.resistancesocialnetwork.repository.repositoriesimpl;


import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.InventoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

interface InventoryRepositoryJpa extends JpaRepository<Inventory, Integer> {
}

@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    private final InventoryRepositoryJpa adapter;

    public InventoryRepositoryImpl(InventoryRepositoryJpa adapter) {
        this.adapter = adapter;
    }

    @Override
    public void save(Inventory inventory) {
        adapter.save(inventory);
    }

    @Override
    public void saveAll(List<Inventory> inventories) {
        adapter.saveAll(inventories);
    }

    @Override
    public Optional<Inventory> findById(Integer id) {
        return adapter.findById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return adapter.existsById(id);
    }

    @Override
    public List<Inventory> findAll() {
        return adapter.findAll();
    }
}
