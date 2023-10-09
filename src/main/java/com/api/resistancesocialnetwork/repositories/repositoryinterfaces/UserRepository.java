package com.api.resistancesocialnetwork.repositories.repositoryinterfaces;

import com.api.resistancesocialnetwork.domain.user.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Integer id);
    Optional<UserDetails> findByLogin(String login);
    Optional<User> findUserByLogin(String login);
    void save(User user);
}
