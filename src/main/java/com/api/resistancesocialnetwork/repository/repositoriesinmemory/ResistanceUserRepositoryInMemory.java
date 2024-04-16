package com.api.resistancesocialnetwork.repository.repositoriesinmemory;

import com.api.resistancesocialnetwork.entity.ResistanceUser;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.ResistanceUserRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResistanceUserRepositoryInMemory implements ResistanceUserRepository {
    private final List<ResistanceUser> users;

    public ResistanceUserRepositoryInMemory() {
        this.users = new ArrayList<>();
    }

    @Override
    public Optional<ResistanceUser> findUserBy(Integer id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<UserDetails> findUserDetailsBy(String login) {
        return Optional.empty();
    }

    @Override
    public Optional<ResistanceUser> findUserBy(String login) {
        return users.stream().filter(user -> user.getLogin().equals(login)).findFirst();
    }

    @Override
    public void save(ResistanceUser user) {
        users.add(user);
    }

    @Override
    public void delete(ResistanceUser user) {
        users.remove(user);
    }

    @Override
    public List<ResistanceUser> findAll() {
        return users;
    }

    public void saveInMem(ResistanceUser user) {
        users.add(user);
    }

    public void saveAllInMem(List<ResistanceUser> newUsers) {
        users.addAll(newUsers);
    }
}
