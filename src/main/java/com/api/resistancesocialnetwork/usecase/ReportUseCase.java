package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.entity.ResistanceUser;
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

    public void handle(ReportFacade reportFacade, ResistanceUser user) throws ResistanceException {
        ReportFacade report = Optional.ofNullable(reportFacade).orElseThrow(
                () -> new ResistanceException("Must provide id of rebel to be reported.")
        );

        Rebel reporting = user.getRebel().orElseThrow(
                () -> new ResistanceException("Reporting user must set rebel profile first.")
        );

        Rebel reported = rebelRepository.findById(report.reportedId()).orElseThrow(
                () -> new ResistanceException("Rebel to be reported not found.")
        );

        reportRules.handle(reporting, reported);

        reported.setReportCounterUp();
        reporting.addToReportedRebels(reported.getId());

        rebelRepository.saveAll(Arrays.asList(reporting, reported));
    }
}