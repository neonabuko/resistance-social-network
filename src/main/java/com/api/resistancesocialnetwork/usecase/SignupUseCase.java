package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.ItemRepository;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.LocationRepository;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.RebelRepository;
import com.api.resistancesocialnetwork.request.DTO.SignupDTO;
import com.api.resistancesocialnetwork.rules.SignupRules;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SignupUseCase {
    private final RebelRepository rebelRepo;
    private final LocationRepository locationRepo;
    private final InventoryRepository inventoryRepo;
    private final ItemRepository itemRepository;
    private final SignupRules signUpRules;

    public SignupUseCase(RebelRepository rebelRepo,
                         LocationRepository locationRepo,
                         InventoryRepository inventoryRepo,
                         ItemRepository itemRepository, SignupRules signUpRules) {
        this.rebelRepo = rebelRepo;
        this.locationRepo = locationRepo;
        this.inventoryRepo = inventoryRepo;
        this.itemRepository = itemRepository;
        this.signUpRules = signUpRules;
    }

    public void handle(SignupDTO signupDTO) {
        signUpRules.handle(
                 signupDTO.rebel(),
                 signupDTO.location(),
                 signupDTO.inventory()
         );

        Location formattedLocation = signupDTO.location();
        Rebel formattedRebel = signupDTO.rebel();
        Inventory formattedInventory = signupDTO.inventory();

        formattedLocation.setRebel(formattedRebel);
        locationRepo.save(formattedLocation);

        formattedInventory.setRebel(formattedRebel);
        inventoryRepo.save(signupDTO.inventory());

        rebelRepo.save(formattedRebel);
        itemRepository.saveAll(formattedInventory.getItems());
    }
}
