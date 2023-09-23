package com.api.resistancesocialnetwork.repositories.repositoriesindatabase;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.RebelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

interface RebelRepositoryJpa extends JpaRepository<Rebel, Integer> {
}

@Component
public class RebelRepositoryInDatabase implements RebelRepository {

    private final RebelRepositoryJpa adapter;

    @Autowired
    public RebelRepositoryInDatabase(RebelRepositoryJpa adapter) {
        this.adapter = adapter;
    }

    @Override
    public void save(Rebel rebel) {
        adapter.save(rebel);
    }

    @Override
    public Optional<Rebel> findById(Integer id) {
        return adapter.findById(id);
    }

    @Override
    public boolean existsById(Integer mustProvideSourceRebelId) {
        return adapter.existsById(mustProvideSourceRebelId);
    }

    @Override
    public List<Rebel> findAll() {
        return adapter.findAll();
    }

}
