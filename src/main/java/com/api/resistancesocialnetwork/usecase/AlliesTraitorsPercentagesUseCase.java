package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.RebelRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AlliesTraitorsPercentagesUseCase {
    private final RebelRepository rebelRepository;

    public AlliesTraitorsPercentagesUseCase(RebelRepository rebelRepository) {
        this.rebelRepository = rebelRepository;
    }

    public List<Double> handle() {
        List<Rebel> rebelsList = rebelRepository.findAll();

        if (rebelsList.isEmpty()) {
            return List.of();
        }

        double total_REBELS = rebelsList.size();
        double total_TRAITORS = rebelsList.stream().filter(Rebel::isTraitor).count();

        double TRAITOR_portion = total_TRAITORS / total_REBELS;
        double ALLY_portion = 1 - TRAITOR_portion;

        return Arrays.asList(ALLY_portion, TRAITOR_portion);
    }
}
