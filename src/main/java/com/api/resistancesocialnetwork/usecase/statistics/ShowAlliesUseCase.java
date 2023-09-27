package com.api.resistancesocialnetwork.usecase.statistics;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.RebelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ShowAlliesUseCase {
    private final RebelRepository rebelRepo;

    public ShowAlliesUseCase(RebelRepository rebelRepo) {
        this.rebelRepo = rebelRepo;
    }

    public List<String> handle() {
        List<String> allies = new ArrayList<>();
        List<Rebel> rebelsList = rebelRepo.findAll();

        if (rebelsList.isEmpty()) return allies;

        for (Rebel rebel : rebelsList) {
            allies.addAll(Arrays.asList(
                    rebel.toString(),
                    rebel.getLocation().toString(),
                    rebel.getInventory().toString()
            ));
        }

        return allies;
    }
}
