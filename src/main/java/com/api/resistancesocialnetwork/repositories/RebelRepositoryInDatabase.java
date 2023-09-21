package com.api.resistancesocialnetwork.repositories;

import com.api.resistancesocialnetwork.model.Rebel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
@Repository
interface RebelRepositoryJpa extends JpaRepository<Rebel, Integer> {
}

