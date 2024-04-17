package com.api.resistancesocialnetwork.usecase.statistics;

import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.RebelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlliesUseCase {
    private final RebelRepository rebelRepo;
    public AlliesUseCase(RebelRepository rebelRepo) {
        this.rebelRepo = rebelRepo;
    }

    public List<Rebel> handle() {
        List<Rebel> allies = new ArrayList<>();
        List<Rebel> rebelsList = rebelRepo.findAll();

        for (Rebel rebel : rebelsList) {
            if (rebel.getReportCounter() < 3) allies.add(rebel);
        }

        return allies;
    }
}
