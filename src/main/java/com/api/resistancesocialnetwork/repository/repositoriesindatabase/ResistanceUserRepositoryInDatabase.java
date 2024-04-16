package com.api.resistancesocialnetwork.repository.repositoriesindatabase;

import com.api.resistancesocialnetwork.entity.ResistanceUser;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.ResistanceUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

interface ResistanceUserRepositoryJpa extends JpaRepository<ResistanceUser, Integer> {
    Optional<UserDetails> findByLogin(String login);
}

@Component
public class ResistanceUserRepositoryInDatabase implements ResistanceUserRepository {
    private final ResistanceUserRepositoryJpa adapter;
    public ResistanceUserRepositoryInDatabase(ResistanceUserRepositoryJpa resistanceUserRepositoryJpa) {
        this.adapter = resistanceUserRepositoryJpa;
    }
    @Override
    public Optional<ResistanceUser> findUserBy(Integer id) {
        return adapter.findById(id);
    }
    @Override
    public Optional<UserDetails> findUserDetailsBy(String login) {
        return adapter.findByLogin(login);
    }

    @Override
    public Optional<ResistanceUser> findUserBy(String login) {
        return adapter.findAll().stream().filter(user -> user.getLogin().equals(login)).findFirst();
    }
    @Override
    public void save(ResistanceUser user) {
        adapter.save(user);
    }

    @Override
    public void delete(ResistanceUser user) {
        adapter.delete(user);
    }

    @Override
    public List<ResistanceUser> findAll() {
        return adapter.findAll();
    }
}
