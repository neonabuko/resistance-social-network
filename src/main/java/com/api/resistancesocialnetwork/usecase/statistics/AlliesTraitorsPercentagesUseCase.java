package com.api.resistancesocialnetwork.usecase.statistics;

import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.RebelRepository;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

@Service
public class AlliesTraitorsPercentagesUseCase {
    private final RebelRepository rebelRepository;

    public AlliesTraitorsPercentagesUseCase(RebelRepository rebelRepository) {
        this.rebelRepository = rebelRepository;
    }

    public List<String> handle() {
        List<Rebel> rebelsList = rebelRepository.findAll();

        if (rebelsList.isEmpty()) {
            return List.of();
        }

        double REBELS = rebelsList.size();
        double TRAITORS = rebelsList.stream().filter(Rebel::isTraitor).count();

        double TRAITOR_PART = TRAITORS / REBELS;
        double ALLY_PART = 1 - TRAITOR_PART;

        NumberFormat toPercent = NumberFormat.getPercentInstance();
        return Arrays.asList(toPercent.format(ALLY_PART), toPercent.format(TRAITOR_PART));
    }
}
