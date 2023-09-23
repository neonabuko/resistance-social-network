package com.api.resistancesocialnetwork.repositories.repositoriesinmemory;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.RebelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RebelRepositoryInMemory implements RebelRepository {
    private final List<Rebel> rebelList = new ArrayList<>();

    public void saveInMem(Rebel rebel) {
        rebelList.add(rebel);
    }

    @Override
    public void save(Rebel rebel) {

    }

    public void deleteAllInMem() {
        rebelList.clear();
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
