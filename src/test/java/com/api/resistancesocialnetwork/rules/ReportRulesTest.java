package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.usecase.ReportUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportRulesTest {
    private final RebelRepositoryInMemory rebelRepositoryinMemory = new RebelRepositoryInMemory();
    private final ReportRules reportRules = new ReportRules(rebelRepositoryinMemory);
    private final Rebel rebel1 = new Rebel("cadeirudo", 20, "female");
    private final Rebel rebel2 = new Rebel("linguica", 30, "male");

    @BeforeEach
    void setUp() {
        rebel1.setId(1);
        rebel2.setId(2);
        rebelRepositoryinMemory.saveInMem(rebel1);
        rebelRepositoryinMemory.saveInMem(rebel2);
    }

    @Test
    void should_throw_Exception_when_sourceId_not_provided() {
        Exception e = assertThrows(Exception.class, () -> reportRules.handle(null, 11));
        assertTrue(e.getMessage().contains("must provide source rebel id"));
    }

    @Test
    void should_throw_Exception_when_targetId_not_provided() {
        Exception e = assertThrows(Exception.class, () -> reportRules.handle(rebel1.getId(), null));
        System.out.println(e.getMessage());
        assertTrue(e.getMessage().contains("must provide target rebel id"));
    }

    @Test
    void should_throw_Exception_when_source_rebel_not_found() {
        Exception e = assertThrows(Exception.class, () -> reportRules.handle(32, 11));
        assertTrue(e.getMessage().contains("source rebel not found"));
    }

    @Test
    void should_throw_Exception_when_target_rebel_not_found() {
        Exception e = assertThrows(Exception.class, () -> reportRules.handle(rebel1.getId(), 46));
        assertTrue(e.getMessage().contains("target rebel not found"));
    }

    @Test
    void source_should_not_be_able_to_report_target_when_target_already_reported() throws Exception {
        ReportUseCase reportUseCase = new ReportUseCase(rebelRepositoryinMemory, reportRules);
        reportUseCase.handle(rebel1.getId(), rebel2.getId());

        Exception e = assertThrows(Exception.class, () -> reportRules.handle(rebel1.getId(), rebel2.getId()));
        assertTrue(e.getMessage().contains("rebel already reported"));
    }

    @Test
    void should_throw_IllegalArgumentException_when_source_reports_himself() throws Exception {
        Exception e = assertThrows(Exception.class, () -> reportRules.handle(rebel1.getId(), rebel1.getId()));
        assertTrue(e.getMessage().contains("can not report yourself"));
    }
}