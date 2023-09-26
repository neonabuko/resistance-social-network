package com.api.resistancesocialnetwork.repositories.repositoriesinmemory;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.RebelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RebelRepositoryInMemory implements RebelRepository {
    private final List<Rebel> rebelList;
    private static Integer id;

    public RebelRepositoryInMemory() {
        this.rebelList = new ArrayList<>();
        id = 1;
    }

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

    @Override
    public void saveAll(List<Rebel> rebels) {
        for (Rebel rebel : rebels) {
            rebel.setId(id++);
            rebelList.add(rebel);
        }
    }
}
