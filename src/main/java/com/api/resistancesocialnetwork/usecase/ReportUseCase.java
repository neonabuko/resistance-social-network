package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.RebelRepository;
import com.api.resistancesocialnetwork.rules.ReportRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportUseCase {
    private final RebelRepository rebelRepository;

    @Autowired
    public ReportUseCase(RebelRepository rebelRepository) {
        this.rebelRepository = rebelRepository;
    }

    public void handle(Integer sourceId, Integer targetId) throws Exception {
        new ReportRules(rebelRepository).handle(sourceId, targetId);

        Rebel sourceRebel = rebelRepository.findById(sourceId).get();
        Rebel targetRebel = rebelRepository.findById(targetId).get();

        targetRebel.setReportCounterUp();
        sourceRebel.getReportedRebels().add(targetId);

        rebelRepository.save(sourceRebel);
        rebelRepository.save(targetRebel);
    }
}
