package com.api.resistancesocialnetwork.repositories;

import com.api.resistancesocialnetwork.model.Rebel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RebelRepositoryInMem implements RebelRepository {
    private final List<Rebel> rebelList = new ArrayList<>();
    private static Integer id = 0;


    @Override
    public void save(Rebel rebel) {
        rebel.setId(id++);
        rebelList.add(rebel);
    }

    @Override
    public Optional<Rebel> findById(Integer id) {
        return rebelList.stream().filter(rebel -> rebel.getId().equals(id)).findFirst();
    }

    @Override
    public boolean existsById(Integer id) {
        return rebelList.stream().anyMatch(rebel -> rebel.getId().equals(id));
    }

    @Override
    public List<Rebel> findAll() {
        return rebelList;
    }
}
