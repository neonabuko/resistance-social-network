package com.api.resistancesocialnetwork.unit.rules;

import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.facade.ReportFacade;
import com.api.resistancesocialnetwork.repository.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.rules.ReportRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import com.api.resistancesocialnetwork.usecase.ReportUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportRulesTest {
    private final RebelRepositoryInMemory rebelRepositoryinMem = new RebelRepositoryInMemory();
    private final ReportRules reportRules = new ReportRules();
    private final Rebel rebel1 = new Rebel("cadeirudo", 20, "female");
    private final Rebel rebel2 = new Rebel("linguica", 30, "male");

    @Test
    @DisplayName("rebel should not be able to report himself")
    void should_throw_ResistanceSocialNetworkException_when_self_report(){
        rebelRepositoryinMem.saveInMem(rebel1);
        rebel1.setId(1);
        Exception e = assertThrows(ResistanceSocialNetworkException.class, () -> reportRules.handle(rebel1, rebel1));
        assertTrue(e.getMessage().contains("can not self report"));
    }

    @Test
    @DisplayName("rebel should only be able to report the same rebel once")
    void should_only_be_able_to_report_same_rebel_once() {
        rebelRepositoryinMem.saveInMem(rebel1);
        rebelRepositoryinMem.saveInMem(rebel2);
        rebel1.setId(1);
        rebel2.setId(2);
        ReportFacade reportFacade = new ReportFacade(1, 2);
        ReportUseCase reportUseCase = new ReportUseCase(rebelRepositoryinMem, reportRules);

        reportUseCase.handle(reportFacade);

        Exception e = assertThrows(ResistanceSocialNetworkException.class, () -> reportRules.handle(rebel1, rebel2));
        assertTrue(e.getMessage().contains("rebel already reported"));
    }
}