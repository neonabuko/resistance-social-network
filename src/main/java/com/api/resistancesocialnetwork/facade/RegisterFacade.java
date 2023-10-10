package com.api.resistancesocialnetwork.facade;

import com.api.resistancesocialnetwork.enums.UserRole;

public record RegisterFacade(String login, String password, UserRole role) {
}
