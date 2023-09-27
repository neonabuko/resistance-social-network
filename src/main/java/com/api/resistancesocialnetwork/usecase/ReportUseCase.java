package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.RebelRepository;
import com.api.resistancesocialnetwork.request.facade.ReportFacade;
import com.api.resistancesocialnetwork.rules.ReportRules;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ReportUseCase {
    private final RebelRepository rebelRepository;
    private final ReportRules reportRules;

    public ReportUseCase(RebelRepository rebelRepository, ReportRules reportRules) {
        this.rebelRepository = rebelRepository;
        this.reportRules = reportRules;
    }

    public void handle(ReportFacade reportFacade) throws IllegalArgumentException {
        Rebel sourceRebel = rebelRepository.findById(reportFacade.sourceId()).orElseThrow(
                () -> new IllegalArgumentException("source rebel not found")
        );
        Rebel targetRebel = rebelRepository.findById(reportFacade.targetId()).orElseThrow(
                () -> new IllegalArgumentException("target rebel not found")
        );

        reportRules.handle(sourceRebel, targetRebel);

        targetRebel.setReportCounterUp();
        sourceRebel.addToReportedRebels(targetRebel.getId());

        rebelRepository.saveAll(Arrays.asList(sourceRebel, targetRebel));
    }
}