package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.entity.User;
import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.UserRepository;
import com.api.resistancesocialnetwork.facade.SignupFacade;
import com.api.resistancesocialnetwork.rules.SignupRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SignupUseCase {
    private final SignupRules signUpRules;
    private final UserRepository userRepository;

    public SignupUseCase(SignupRules signUpRules,
                         UserRepository userRepository) {
        this.signUpRules = signUpRules;
        this.userRepository = userRepository;
    }

    public void handle(SignupFacade signup, String login) throws ResistanceSocialNetworkException {
        signUpRules.handle(signup);

        Location formattedLocation = signup.location();
        Rebel formattedRebel = signup.rebel();
        Inventory formattedInventory = signup.inventory();

        formattedRebel.setLocation(formattedLocation);
        formattedRebel.setInventory(formattedInventory);

        User relatedUser = userRepository.findUserByLogin(login).get();

        relatedUser.setRebel(formattedRebel);
        relatedUser.setLocation(formattedLocation);
        relatedUser.setInventory(formattedInventory);

        userRepository.save(relatedUser);
    }
}
