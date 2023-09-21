package com.api.resistancesocialnetwork.repositories;

import com.api.resistancesocialnetwork.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository{

    void save(Item item);

    void saveAll(List<Item> items);
}
