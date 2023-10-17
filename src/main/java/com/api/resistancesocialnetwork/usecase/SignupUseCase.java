package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.entity.User;
import com.api.resistancesocialnetwork.facade.SignupFacade;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.UserRepository;
import com.api.resistancesocialnetwork.rules.SignupRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignupUseCase {
    private final UserRepository userRepository;
    private final SignupRules signupRules;
    public SignupUseCase(UserRepository userRepository, SignupRules signupRules) {
        this.userRepository = userRepository;
        this.signupRules = signupRules;
    }

    public void handle(SignupFacade data) {
        signupRules.handle(data);
        String username = data.username();
        String password = data.password();
        var role = data.getRole();

        if (userRepository.findUserBy(username).isPresent())
            throw new ResistanceException("username '" + username + "' already taken");
        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        User user = new User(username, encryptedPassword, role);
        userRepository.save(user);
    }
}
