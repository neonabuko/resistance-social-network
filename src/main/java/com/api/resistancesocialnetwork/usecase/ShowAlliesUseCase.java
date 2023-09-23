package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.LocationRepository;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.RebelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ShowAlliesUseCase {
    private final RebelRepository rebelRepo;
    private final LocationRepository locationRepo;
    private final InventoryRepository inventoryRepo;

    public ShowAlliesUseCase(RebelRepository rebelRepo,
                             LocationRepository locationRepo,
                             InventoryRepository inventoryRepo) {
        this.rebelRepo = rebelRepo;
        this.locationRepo = locationRepo;
        this.inventoryRepo = inventoryRepo;
    }

    public List<String> handle() {
        List<String> allies = new ArrayList<>();
        List<Rebel> rebelsList = rebelRepo.findAll();

        if (rebelsList.isEmpty()) return allies;

        for (Rebel rebel : rebelsList) {
            allies.addAll(Arrays.asList(
                    rebel.toString(),
                    locationRepo.findById(rebel.getId()).get().toString(),
                    inventoryRepo.findById(rebel.getId()).get().toString()
            ));
        }

        return allies;
    }
}
