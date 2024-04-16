package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.entity.ResistanceUser;
import com.api.resistancesocialnetwork.facade.SignupFacade;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.ResistanceUserRepository;
import com.api.resistancesocialnetwork.rules.SignupRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignupUseCase {
    private final ResistanceUserRepository resistanceUserRepository;
    private final SignupRules signupRules;
    public SignupUseCase(ResistanceUserRepository resistanceUserRepository, SignupRules signupRules) {
        this.resistanceUserRepository = resistanceUserRepository;
        this.signupRules = signupRules;
    }

    public void handle(SignupFacade data) {
        signupRules.handle(data);
        String username = data.username();
        String password = data.password();
        var role = data.getRole();

        if (resistanceUserRepository.findUserBy(username).isPresent())
            throw new ResistanceException("username '" + username + "' already taken");
        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        ResistanceUser user = new ResistanceUser(username, encryptedPassword, role);
        resistanceUserRepository.save(user);
    }
}
