package com.api.resistancesocialnetwork.usecase;

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
                 signupDTO.getRebel(),
                 signupDTO.getLocation(),
                 signupDTO.getInventory()
         );

        signupDTO.getLocation().setRebel(signupDTO.getRebel());
        locationRepo.save(signupDTO.getLocation());

        signupDTO.getInventory().setRebel(signupDTO.getRebel());
        inventoryRepo.save(signupDTO.getInventory());

        rebelRepo.save(signupDTO.getRebel());
        itemRepository.saveAll(signupDTO.getInventory().getItems());
    }
}
