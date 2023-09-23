package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.RebelRepository;
import com.api.resistancesocialnetwork.rules.ReportRules;
import org.springframework.stereotype.Service;

@Service
public class ReportUseCase {
    private final RebelRepository rebelRepository;
    private final ReportRules reportRules;

    public ReportUseCase(RebelRepository rebelRepository, ReportRules reportRules) {
        this.rebelRepository = rebelRepository;
        this.reportRules = reportRules;
    }

    public void handle(Integer sourceId, Integer targetId) throws IllegalArgumentException {
        reportRules.handle(sourceId, targetId);

        Rebel sourceRebel = rebelRepository.findById(sourceId).get();
        Rebel targetRebel = rebelRepository.findById(targetId).get();

        targetRebel.setReportCounterUp();
        sourceRebel.getReportedRebels().add(targetId);

        rebelRepository.save(sourceRebel);
        rebelRepository.save(targetRebel);
    }
}