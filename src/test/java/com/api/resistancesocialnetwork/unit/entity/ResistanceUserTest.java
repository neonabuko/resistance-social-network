package com.api.resistancesocialnetwork.unit.entity;

import com.api.resistancesocialnetwork.entity.ResistanceUser;
import com.api.resistancesocialnetwork.enums.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResistanceUserTest {

    @Test
    @DisplayName("Should set user login correctly")
    void should_set_user_login() {
        var newUser = new ResistanceUser();
        newUser.setLogin("new user");

        assertEquals(newUser.getLogin(), "new user");
    }

    @Test
    @DisplayName("Should set user password correctly")
    void should_set_user_password() {
        var newUser = new ResistanceUser();
        newUser.setPassword("123");

        assertEquals(newUser.getPassword(), "123");
    }

    @Test
    @DisplayName("Should set user role correctly")
    void should_set_role() {
        var newUser = new ResistanceUser();
        newUser.setRole(UserRole.ADMIN);

        assertEquals(newUser.getRole(), UserRole.ADMIN);
    }
}