package com.api.resistancesocialnetwork.usecase.statistics;

import com.api.resistancesocialnetwork.entity.Rebel;
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
            if (rebel.getReportCounter() < 3) {
                allies.addAll(Arrays.asList(
                        rebel.toString(),
                        rebel.getLocation().toString(),
                        rebel.getInventory().toString(),
                        "\n" + "─".repeat(50) + "\n"
                ));
            }
        }

        return allies;
    }
}
