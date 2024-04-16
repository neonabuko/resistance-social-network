package com.api.resistancesocialnetwork.repository.repositoryinterfaces;

import com.api.resistancesocialnetwork.entity.ResistanceUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface ResistanceUserRepository {
    Optional<ResistanceUser> findUserBy(Integer id);
    Optional<ResistanceUser> findUserBy(String login);
    Optional<UserDetails> findUserDetailsBy(String login);
    void save(ResistanceUser user);
    void delete(ResistanceUser user);
    List<ResistanceUser> findAll();
}
