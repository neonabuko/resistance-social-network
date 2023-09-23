package com.api.resistancesocialnetwork.repositories.repositoriesindatabase;


import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

interface InventoryRepositoryJpa extends JpaRepository<Inventory, Integer> {
    default Optional<Item> findItemByName(Integer id, String itemName) {
        if (findById(id).isPresent())
            return findById(id).get().getItems().stream().filter(item -> item.getName().equals(itemName)).findFirst();
        else return Optional.empty();
    }
}

@Component
public class InventoryRepositoryInDatabase implements InventoryRepository {

    private final InventoryRepositoryJpa adapter;

    @Autowired
    public InventoryRepositoryInDatabase(InventoryRepositoryJpa adapter) {
        this.adapter = adapter;
    }

    @Override
    public void save(Inventory inventory) {
        adapter.save(inventory);
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

    @Override
    public Optional<Item> findItemByName(Integer id, String itemName) {
        return adapter.findItemByName(id, itemName);
    }
}
