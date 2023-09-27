package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.LocationRepository;
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
    private final LocationRepository locationRepo;
    private final InventoryRepository inventoryRepo;
    private final SignupRules signUpRules;

    public SignupUseCase(RebelRepository rebelRepo,
                         LocationRepository locationRepo,
                         InventoryRepository inventoryRepo,
                         SignupRules signUpRules) {
        this.rebelRepo = rebelRepo;
        this.locationRepo = locationRepo;
        this.inventoryRepo = inventoryRepo;
        this.signUpRules = signUpRules;
    }

    public void handle(SignupFacade signupFacade) throws ResistanceSocialNetworkException {
        signUpRules.handle(signupFacade);

        Location formattedLocation = signupFacade.location();
        Rebel formattedRebel = signupFacade.rebel();
        Inventory formattedInventory = signupFacade.inventory();

        formattedLocation.setRebel(formattedRebel);
        locationRepo.save(formattedLocation);

        formattedInventory.setRebel(formattedRebel);
        inventoryRepo.save(signupFacade.inventory());

        formattedRebel.setLocation(formattedLocation);
        formattedRebel.setInventory(formattedInventory);
        rebelRepo.save(formattedRebel);
    }
}
