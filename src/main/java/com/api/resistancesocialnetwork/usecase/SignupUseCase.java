package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.RebelRepository;
import com.api.resistancesocialnetwork.request.facade.SignupFacade;
import com.api.resistancesocialnetwork.rules.SignupRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SignupUseCase {
    private final RebelRepository rebelRepo;
    private final SignupRules signUpRules;

    public SignupUseCase(RebelRepository rebelRepo, SignupRules signUpRules) {
        this.rebelRepo = rebelRepo;
        this.signUpRules = signUpRules;
    }

    public void handle(SignupFacade signupFacade) throws ResistanceSocialNetworkException {
        signUpRules.handle(signupFacade);

        Location formattedLocation = signupFacade.location();
        Rebel formattedRebel = signupFacade.rebel();
        Inventory formattedInventory = signupFacade.inventory();

        formattedRebel.setLocation(formattedLocation);
        formattedRebel.setInventory(formattedInventory);
        rebelRepo.save(formattedRebel);
    }
}
