package com.api.resistancesocialnetwork.usecase.statistics;

import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.facade.stats.AlliesTraitorsPercentagesFacade;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.RebelRepository;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.List;

@Service
public class AlliesTraitorsPercentagesUseCase {
    private final RebelRepository rebelRepository;

    public AlliesTraitorsPercentagesUseCase(RebelRepository rebelRepository) {
        this.rebelRepository = rebelRepository;
    }

    public AlliesTraitorsPercentagesFacade handle() {
        List<Rebel> rebelsList = rebelRepository.findAll();

        if (rebelsList.isEmpty()) throw new ResistanceException("No allies/traitors percentages to show.");

        double REBELS = rebelsList.size();
        double TRAITORS = rebelsList.stream().filter(Rebel::isTraitor).count();

        double TRAITOR_PART = TRAITORS / REBELS;
        double ALLY_PART = 1 - TRAITOR_PART;

        NumberFormat toPercent = NumberFormat.getPercentInstance();

        return new AlliesTraitorsPercentagesFacade(
                toPercent.format(ALLY_PART),
                toPercent.format(TRAITOR_PART)
        );
    }
}
