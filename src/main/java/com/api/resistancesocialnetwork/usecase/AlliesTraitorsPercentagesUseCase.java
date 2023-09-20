package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.RebelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
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

    public List<String> handle(){
        double allIndividuals = rebelRepository.findAll().size();
        double traitors = rebelRepository.findAll().stream().filter(Rebel::isTraitor).count();
        double traitorsDecimal = traitors / allIndividuals;
        double alliesDecimal = 1 - traitorsDecimal;
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        String traitorPercentage = numberFormat.format(traitorsDecimal);
        String alliesPercentage = numberFormat.format(alliesDecimal);

        return new ArrayList<>(Arrays.asList(alliesPercentage, traitorPercentage));
    }
}
