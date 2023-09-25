package com.api.resistancesocialnetwork.repositories.repositoriesindatabase;

import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

interface ItemRepositoryJpa extends JpaRepository<Item, Integer> {
}

@Component
public class ItemRepositoryInDatabase implements ItemRepository {

    private final ItemRepositoryJpa adapter;

    @Autowired
    public ItemRepositoryInDatabase(ItemRepositoryJpa adapter) {
        this.adapter = adapter;
    }

    @Override
    public void save(Item item) {
        adapter.save(item);
    }


    @Override
    public Optional<Item> findById(Integer id) {
        return adapter.findById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return adapter.existsById(id);
    }

    @Override
    public List<Item> findAll() {
        return adapter.findAll();
    }

    @Override
    public void saveAll(List<Item> items) {
        adapter.saveAll(items);
    }
}
