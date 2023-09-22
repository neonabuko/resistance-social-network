package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Inventory;
import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.ItemRepository;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.LocationRepository;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.RebelRepository;
import com.api.resistancesocialnetwork.rules.SignupRules;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignupUseCase {
    private final RebelRepository rebelRepo;
    private final LocationRepository locationRepo;
    private final InventoryRepository inventoryRepo;
    private final ItemRepository itemRepository;

    public SignupUseCase(RebelRepository rebelRepo, LocationRepository locationRepo,
                         InventoryRepository inventoryRepo, ItemRepository itemRepository) {
        this.rebelRepo = rebelRepo;
        this.locationRepo = locationRepo;
        this.inventoryRepo = inventoryRepo;
        this.itemRepository = itemRepository;
    }
    @Transactional
    public void handle(Rebel rebel, Location location, Inventory inventory) {
        List<?> formattedStats = new SignupRules().format(rebel, location, inventory);

        Inventory formattedInventory = (Inventory) formattedStats.get(2);
        itemRepository.saveAll(formattedInventory.getItems());
        rebelRepo.save((Rebel) formattedStats.get(0));
        locationRepo.save((Location) formattedStats.get(1));
        inventoryRepo.save((Inventory) formattedStats.get(2));
        rebel.setLocation(location);
        rebel.setInventory(inventory);
        inventory.setItems(inventory.getItems());
        location.setRebel(rebel);
        inventory.setRebel(rebel);
    }
}
