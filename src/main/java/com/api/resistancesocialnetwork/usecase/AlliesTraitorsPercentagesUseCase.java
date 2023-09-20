package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.RebelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AlliesTraitorsPercentagesUseCase {

    private final RebelRepository rebelRepository;
    @Autowired
    public AlliesTraitorsPercentagesUseCase(RebelRepository rebelRepository) {
        this.rebelRepository = rebelRepository;
    }

    public List<Double> handle() {
        List<Rebel> allRebels = rebelRepository.findAll();

        double allRebelsCount = allRebels.size();
        double traitors = allRebels.stream().filter(Rebel::isTraitor).count();

        double traitorsDecimal = traitors / allRebelsCount;
        double alliesDecimal = 1 - traitorsDecimal;

        return new ArrayList<>(Arrays.asList(alliesDecimal, traitorsDecimal));
    }
}
