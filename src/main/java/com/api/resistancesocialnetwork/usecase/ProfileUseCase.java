package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.entity.ResistanceUser;
import com.api.resistancesocialnetwork.facade.ProfileFacade;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.ResistanceUserRepository;
import com.api.resistancesocialnetwork.rules.ProfileRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProfileUseCase {
    private final ProfileRules signUpRules;
    private final ResistanceUserRepository resistanceUserRepository;

    public ProfileUseCase(ProfileRules signUpRules, ResistanceUserRepository resistanceUserRepository) {
        this.signUpRules = signUpRules;
        this.resistanceUserRepository = resistanceUserRepository;
    }

    public void handle(ProfileFacade signup, String login) throws ResistanceException {
        ResistanceUser user = resistanceUserRepository.findUserBy(login).orElseThrow(
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

        resistanceUserRepository.save(user);
    }
}
