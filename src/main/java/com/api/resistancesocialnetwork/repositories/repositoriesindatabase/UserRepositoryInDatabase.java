package com.api.resistancesocialnetwork.repositories.repositoriesindatabase;

import com.api.resistancesocialnetwork.domain.user.User;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

interface UserRepositoryJpa extends JpaRepository<User, String> {
    UserDetails findByLogin(String login);
}

class UserRepositoryInDatabase implements UserRepository {

    @Override
    public User findById() {
        return null;
    }
}
