package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.entity.User;
import com.api.resistancesocialnetwork.facade.RegisterFacade;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RegisterUseCase {
    private final UserRepository userRepository;
    public RegisterUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void handle(RegisterFacade data) {
        var username = data.getUsername().orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "must provide a username")
        );
        var password = data.getPassword().orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "must provide a password")
        );
        var role = data.getRole();

        if (userRepository.findByLogin(username).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username '" + username + "' already taken");
        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        User user = new User(username, encryptedPassword, role);
        userRepository.save(user);
    }
}
