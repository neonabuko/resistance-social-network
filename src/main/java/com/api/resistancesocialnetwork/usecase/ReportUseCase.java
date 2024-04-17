package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.facade.ReportFacade;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.RebelRepository;
import com.api.resistancesocialnetwork.rules.ReportRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
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

    public void handle(ReportFacade reportFacade) throws ResistanceException {
        ReportFacade report = Optional.ofNullable(reportFacade).orElse(new ReportFacade(0, 0));
        Rebel reporting = rebelRepository.findById(report.reportingId()).orElseThrow(
                () -> new ResistanceException("reporting rebel not found")
        );
        Rebel reported = rebelRepository.findById(report.reportedId()).orElseThrow(
                () -> new ResistanceException("reported rebel not found")
        );

        reportRules.handle(reporting, reported);

        reported.setReportCounterUp();
        reporting.addToReportedRebels(reported.getId());

        rebelRepository.saveAll(Arrays.asList(reporting, reported));
    }
}