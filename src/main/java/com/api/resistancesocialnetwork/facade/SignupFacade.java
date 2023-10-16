package com.api.resistancesocialnetwork.facade;

import com.api.resistancesocialnetwork.enums.UserRole;

import java.util.Optional;

public record SignupFacade(String username, String password, UserRole role) {
    public Optional<String> getUsername() {
        return Optional.ofNullable(username);
    }

    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }

    public UserRole getRole() {
        return Optional.ofNullable(role).orElse(UserRole.USER);
    }
}
