package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.entity.User;
import com.api.resistancesocialnetwork.facade.SignupFacade;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.UserRepository;
import com.api.resistancesocialnetwork.rules.SignupRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class SignupUseCase {
    private final SignupRules signUpRules;
    private final UserRepository userRepository;

    public SignupUseCase(SignupRules signUpRules, UserRepository userRepository) {
        this.signUpRules = signUpRules;
        this.userRepository = userRepository;
    }

    public void handle(SignupFacade signup, String login) throws ResistanceSocialNetworkException {
        signUpRules.handle(signup);

        Rebel rebel = signup.rebel();
        Location location = signup.location();
        Inventory inventory = signup.inventory();

        rebel.setLocation(location);
        rebel.setInventory(inventory);

        User user = userRepository.findUserBy(login).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "user not found")
        );
        user.setRebel(rebel);
        user.setLocation(location);
        user.setInventory(inventory);

        userRepository.save(user);
    }
}
