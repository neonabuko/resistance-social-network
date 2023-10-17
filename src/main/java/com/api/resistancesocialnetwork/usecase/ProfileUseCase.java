package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.entity.User;
import com.api.resistancesocialnetwork.facade.ProfileFacade;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.UserRepository;
import com.api.resistancesocialnetwork.rules.ProfileRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProfileUseCase {
    private final ProfileRules signUpRules;
    private final UserRepository userRepository;

    public ProfileUseCase(ProfileRules signUpRules, UserRepository userRepository) {
        this.signUpRules = signUpRules;
        this.userRepository = userRepository;
    }

    public void handle(ProfileFacade signup, String login) throws ResistanceException {
        User user = userRepository.findUserBy(login).orElseThrow(
                () -> new ResistanceException("user not found")
        );
        if (user.getRebel().isPresent()) throw new ResistanceException("profile already set");

        signUpRules.handle(signup);

        Rebel rebel = signup.rebel();
        Location location = signup.location();
        Inventory inventory = signup.inventory();

        rebel.setLocation(location);
        rebel.setInventory(inventory);

        user.setRebel(rebel);
        user.setLocation(location);
        user.setInventory(inventory);

        userRepository.save(user);
    }
}
