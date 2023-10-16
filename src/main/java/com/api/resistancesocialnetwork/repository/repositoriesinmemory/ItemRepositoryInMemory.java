package com.api.resistancesocialnetwork.repository.repositoriesinmemory;

import com.api.resistancesocialnetwork.entity.Item;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemRepositoryInMemory implements ItemRepository {
    private final List<Item> itemList;

    public ItemRepositoryInMemory() {
        itemList = new ArrayList<>();
    }

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
