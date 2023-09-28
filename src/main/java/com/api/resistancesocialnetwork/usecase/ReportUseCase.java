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

    public void handle(ReportFacade reportFacade) throws ResistanceSocialNetworkException {
        ReportFacade report = Optional.ofNullable(reportFacade).orElse(new ReportFacade(0, 0));

        Rebel reporting = rebelRepository.findById(report.reportingId()).orElseThrow(
                () -> new ResistanceSocialNetworkException("reporting rebel not found")
        );
        Rebel reported = rebelRepository.findById(report.reportedId()).orElseThrow(
                () -> new ResistanceSocialNetworkException("reported rebel not found")
        );

        reportRules.handle(reporting, reported);

        reported.setReportCounterUp();
        reporting.addToReportedRebels(reported.getId());

        rebelRepository.saveAll(Arrays.asList(reporting, reported));
    }
}