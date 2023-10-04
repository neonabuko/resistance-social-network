package com.api.resistancesocialnetwork.repositories.repositoryinterfaces;

import com.api.resistancesocialnetwork.entity.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    void save(Item item);

    Optional<Item> findById(Integer id);

    boolean existsById(Integer id);

    List<Item> findAll();

    void saveAll(List<Item> items);
}
