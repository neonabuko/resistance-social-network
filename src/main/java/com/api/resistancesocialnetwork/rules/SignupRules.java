package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.facade.SignupFacade;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import org.springframework.stereotype.Component;

@Component
public class SignupRules {

    public void handle(SignupFacade signupFacade) {
        String username = signupFacade.getUsername().orElseThrow(
                () -> new ResistanceException("must provide a username")
        );
        String password = signupFacade.getPassword().orElseThrow(
                () -> new ResistanceException("must provide a password")
        );
        assertUsernameNotOver30Characters(username);
        assertPasswordNotOver30Characters(password);
    }

    private void assertUsernameNotOver30Characters(String username) {
        if (username.length() > 30) throw new ResistanceException("username must have up to 30 characters");
    }

    private void assertPasswordNotOver30Characters(String password) {
        if (password.length() > 30) throw new ResistanceException("password must have up to 30 characters");
    }
}
