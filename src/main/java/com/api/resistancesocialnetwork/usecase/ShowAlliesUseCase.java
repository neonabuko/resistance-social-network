package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.LocationRepository;
import com.api.resistancesocialnetwork.repositories.RebelRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ShowAlliesUseCase {
    private final RebelRepository rebelRepo;
    private final LocationRepository locationRepo;
    private final InventoryRepository inventoryRepo;

    @Autowired
    public ShowAlliesUseCase(RebelRepository rebelRepo, LocationRepository locationRepo, InventoryRepository inventoryRepo) {
        this.rebelRepo = rebelRepo;
        this.locationRepo = locationRepo;
        this.inventoryRepo = inventoryRepo;
    }
    @Transactional
    public List<String> handle() {
        List<String> allies = new ArrayList<>();

        for ( Rebel rebel : rebelRepo.findAll() ) {
            allies.addAll(Arrays.asList(
                    rebel.toString(),
                    locationRepo.findAll().stream().filter(location -> location.getRebel().equals(rebel)).findFirst().get().toString(),
                    inventoryRepo.findAll().stream().filter(inventory -> inventory.getRebel().equals(rebel)).findFirst().get().toString()
            ));
        }
        return allies;
    }
}
