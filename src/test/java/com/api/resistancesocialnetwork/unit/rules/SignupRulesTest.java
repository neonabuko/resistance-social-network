package com.api.resistancesocialnetwork.unit.rules;

import com.api.resistancesocialnetwork.enums.UserRole;
import com.api.resistancesocialnetwork.facade.SignupFacade;
import com.api.resistancesocialnetwork.rules.SignupRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignupRulesTest {
    private final SignupRules signupRules = new SignupRules();
    @Test
    @DisplayName("Should throw exception when username not present")
    void should_throw_exception_when_username_not_present() {
        SignupFacade signupFacade = new SignupFacade(null, "123", UserRole.ADMIN);
        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                signupRules.handle(signupFacade)
        );
        assertTrue(e.getMessage().contains("must provide a username"));
    }
    @Test
    @DisplayName("Should throw exception when password not present")
    void should_throw_exception_when_password_not_present() {
        SignupFacade signupFacade = new SignupFacade("username", null, UserRole.ADMIN);
        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                signupRules.handle(signupFacade)
        );
        assertTrue(e.getMessage().contains("must provide a password"));
    }
    @Test
    @DisplayName("Should throw exception when username over 30 characters")
    void should_throw_exception_when_username_over_30_characters() {
        SignupFacade signupFacade = new SignupFacade("a".repeat(31), "123", UserRole.ADMIN);
        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                signupRules.handle(signupFacade)
        );
        assertTrue(e.getMessage().contains("username must have up to 30 characters"));
    }

    @Test
    @DisplayName("Should throw exception when password over 30 characters")
    void should_throw_exception_when_password_over_30_characters() {
        SignupFacade signupFacade = new SignupFacade("username", "1".repeat(31), UserRole.ADMIN);
        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                signupRules.handle(signupFacade)
        );
        assertTrue(e.getMessage().contains("password must have up to 30 characters"));
    }
}