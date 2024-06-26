package com.api.resistancesocialnetwork.repository.repositoriesimpl;

import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.RebelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

interface RebelRepositoryJpa extends JpaRepository<Rebel, Integer> {
}

@Component
public class RebelRepositoryImpl implements RebelRepository {

    private final RebelRepositoryJpa adapter;

    public RebelRepositoryImpl(RebelRepositoryJpa adapter) {
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
    public boolean existsById(Integer id) {
        return adapter.existsById(id);
    }

    @Override
    public List<Rebel> findAll() {
        return adapter.findAll();
    }

    @Override
    public void saveAll(List<Rebel> rebels) {
        adapter.saveAll(rebels);
    }


}
