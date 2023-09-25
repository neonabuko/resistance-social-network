package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.RebelRepository;
import com.api.resistancesocialnetwork.request.DTO.ReportDTO;
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

    public void handle(ReportDTO reportDTO) throws IllegalArgumentException {
        Rebel sourceRebel = rebelRepository.findById(reportDTO.sourceId()).orElseThrow(
                () -> new IllegalArgumentException("source rebel not found")
        );
        Rebel targetRebel = rebelRepository.findById(reportDTO.targetId()).orElseThrow(
                () -> new IllegalArgumentException("target rebel not found")
        );

        reportRules.handle(sourceRebel, targetRebel);

        targetRebel.setReportCounterUp();
        sourceRebel.addToReportedRebels(targetRebel.getId());

        rebelRepository.saveAll(Arrays.asList(sourceRebel, targetRebel));
    }
}