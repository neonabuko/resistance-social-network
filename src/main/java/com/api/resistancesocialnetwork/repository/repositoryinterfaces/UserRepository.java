package com.api.resistancesocialnetwork.repository.repositoryinterfaces;

import com.api.resistancesocialnetwork.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findUserBy(Integer id);
    Optional<User> findUserBy(String login);
    Optional<UserDetails> findUserDetailsBy(String login);
    void save(User user);
    void delete(User user);
    List<User> findAll();
}
