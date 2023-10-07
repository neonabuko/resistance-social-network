package com.api.resistancesocialnetwork.domain.user.facade;

import com.api.resistancesocialnetwork.domain.user.UserRole;

public record RegisterFacade(String login, String password, UserRole role) {
}
