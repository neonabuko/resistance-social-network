package com.api.resistancesocialnetwork.repositories;

import com.api.resistancesocialnetwork.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemRepositoryInDatabase implements ItemRepository{

    private final ItemRepositoryJpa adapter;

    public ItemRepositoryInDatabase(ItemRepositoryJpa adapter) {
        this.adapter = adapter;
    }

    @Override
    public void save(Item item) {
        adapter.save(item);
    }

    @Override
    public void saveAll(List<Item> items) {

    }

    interface ItemRepositoryJpa extends JpaRepository<Item, Integer>{

    }
}
