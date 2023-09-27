package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.RebelRepository;
import com.api.resistancesocialnetwork.request.facade.ReportFacade;
import com.api.resistancesocialnetwork.rules.ReportRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class ReportUseCase {
    private final RebelRepository rebelRepository;
    private final ReportRules reportRules;

    public ReportUseCase(RebelRepository rebelRepository, ReportRules reportRules) {
        this.rebelRepository = rebelRepository;
        this.reportRules = reportRules;
    }

    public void handle(ReportFacade reportFacadeFacade) throws ResistanceSocialNetworkException {
        ReportFacade reportFacade = Optional.ofNullable(reportFacadeFacade).orElse(new ReportFacade(0, 0));

        Rebel sourceRebel = rebelRepository.findById(reportFacade.sourceId()).orElseThrow(
                () -> new ResistanceSocialNetworkException("reporting rebel not found")
        );
        Rebel targetRebel = rebelRepository.findById(reportFacade.targetId()).orElseThrow(
                () -> new ResistanceSocialNetworkException("reported rebel not found")
        );

        reportRules.handle(sourceRebel, targetRebel);

        targetRebel.setReportCounterUp();
        sourceRebel.addToReportedRebels(targetRebel.getId());

        rebelRepository.saveAll(Arrays.asList(sourceRebel, targetRebel));
    }
}