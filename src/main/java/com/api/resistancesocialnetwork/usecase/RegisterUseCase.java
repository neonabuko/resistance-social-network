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
        if ( userRepository.findByLogin(data.login()).isPresent() )
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username '" + data.login() + "' already taken");
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(data.login(), encryptedPassword, data.role());
        userRepository.save(user);
    }
}
