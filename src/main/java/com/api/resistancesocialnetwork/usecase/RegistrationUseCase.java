package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Item;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.ItemRepository;
import com.api.resistancesocialnetwork.repositories.LocationRepository;
import com.api.resistancesocialnetwork.repositories.RebelRepository;
import com.api.resistancesocialnetwork.rules.RegistrationRules;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationUseCase {
    private final RebelRepository rebelRepo;
    private final LocationRepository locationRepo;
    private final InventoryRepository inventoryRepo;
    private final ItemRepository itemRepository;

    @Autowired
    public RegistrationUseCase(RebelRepository rebelRepo, LocationRepository locationRepo,
                               InventoryRepository inventoryRepo, ItemRepository itemRepository) {
        this.rebelRepo = rebelRepo;
        this.locationRepo = locationRepo;
        this.inventoryRepo = inventoryRepo;
        this.itemRepository = itemRepository;
    }
    @Transactional
    public void handle(Rebel rebel, Location location, Inventory inventory) {
        List<?> formattedStats = new RegistrationRules().format(rebel, location, inventory);

        for (Item item : inventory.getItems()) {
            itemRepository.save(item);
        }

        rebelRepo.save((Rebel) formattedStats.get(0));
        locationRepo.save((Location) formattedStats.get(1));
        inventoryRepo.save((Inventory) formattedStats.get(2));
    }
}
