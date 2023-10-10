package com.api.resistancesocialnetwork.facade;

import java.util.Optional;

public record LoginFacade(String username, String password) {
    public Optional<String> getUsername() {
        return Optional.ofNullable(username);
    }
    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }
}
