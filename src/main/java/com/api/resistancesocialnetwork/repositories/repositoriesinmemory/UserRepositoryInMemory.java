package com.api.resistancesocialnetwork.repositories.repositoriesinmemory;

import com.api.resistancesocialnetwork.entity.User;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryInMemory implements UserRepository {
    private final List<User> users;

    public UserRepositoryInMemory() {
        this.users = new ArrayList<>();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<UserDetails> findByLogin(String login) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return users.stream().filter(user -> user.getLogin().equals(login)).findFirst();
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    public void saveInMem(User user) {
        users.add(user);
    }
}
