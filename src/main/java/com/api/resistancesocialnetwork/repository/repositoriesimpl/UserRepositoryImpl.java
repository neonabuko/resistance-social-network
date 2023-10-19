package com.api.resistancesocialnetwork.repository.repositoriesimpl;

import com.api.resistancesocialnetwork.entity.User;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

interface UserRepositoryJpa extends JpaRepository<User, Integer> {
    Optional<UserDetails> findByLogin(String login);
}

@Component
public class UserRepositoryImpl implements UserRepository {
    private final UserRepositoryJpa adapter;
    public UserRepositoryImpl(UserRepositoryJpa userRepositoryJpa) {
        this.adapter = userRepositoryJpa;
    }
    @Override
    public Optional<User> findUserBy(Integer id) {
        return adapter.findById(id);
    }
    @Override
    public Optional<UserDetails> findUserDetailsBy(String login) {
        return adapter.findByLogin(login);
    }

    @Override
    public Optional<User> findUserBy(String login) {
        return adapter.findAll().stream().filter(user -> user.getLogin().equals(login)).findFirst();
    }
    @Override
    public void save(User user) {
        adapter.save(user);
    }
    @Override
    public void delete(User user) {
        adapter.delete(user);
    }

    @Override
    public List<User> findAll() {
        return adapter.findAll();
    }
}
