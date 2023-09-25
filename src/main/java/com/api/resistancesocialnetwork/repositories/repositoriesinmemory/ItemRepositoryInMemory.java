package com.api.resistancesocialnetwork.repositories.repositoriesinmemory;

import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemRepositoryInMemory implements ItemRepository {
    private final List<Item> itemList = new ArrayList<>();

    @Override
    public void save(Item item) {
        itemList.add(item);
    }

    @Override
    public Optional<Item> findById(Integer id) {
        return itemList.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    @Override
    public boolean existsById(Integer id) {
        return itemList.stream().anyMatch(item -> item.getId().equals(id));
    }

    @Override
    public List<Item> findAll() {
        return itemList;
    }

    @Override
    public void saveAll(List<Item> items) {
        itemList.addAll(items);
    }
}
