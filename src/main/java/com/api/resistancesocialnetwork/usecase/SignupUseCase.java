package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.ItemRepository;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.LocationRepository;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.RebelRepository;
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

    public SignupUseCase(RebelRepository rebelRepo, LocationRepository locationRepo, InventoryRepository inventoryRepo, ItemRepository itemRepository) {
        this.rebelRepo = rebelRepo;
        this.locationRepo = locationRepo;
        this.inventoryRepo = inventoryRepo;
        this.itemRepository = itemRepository;
    }

    public void handle(Rebel rebel, Location location, Inventory inventory) {
        new SignupRules().format(rebel, location, inventory);

        location.setRebel(rebel);
        locationRepo.save(location);

        for (Item item : inventory.getItems()) {
            item.setInventory(inventory);
        }

        inventory.setRebel(rebel);
        inventoryRepo.save(inventory);

        rebelRepo.save(rebel);
        itemRepository.saveAll(inventory.getItems());
    }
}
