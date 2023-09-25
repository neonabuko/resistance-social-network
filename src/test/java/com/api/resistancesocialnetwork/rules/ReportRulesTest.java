package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.repositoriesinmemory.RebelRepositoryInMemory;
import com.api.resistancesocialnetwork.request.DTO.ReportDTO;
import com.api.resistancesocialnetwork.usecase.ReportUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportRulesTest {
    private final RebelRepositoryInMemory rebelRepositoryinMemory = new RebelRepositoryInMemory();
    private final ReportRules reportRules = new ReportRules();
    private final Rebel rebel1 = new Rebel("cadeirudo", 20, "female");
    private final Rebel rebel2 = new Rebel("linguica", 30, "male");

    @BeforeEach
    void setUp() {
        rebel1.setId(1);
        rebel2.setId(2);
        rebelRepositoryinMemory.save(rebel1);
        rebelRepositoryinMemory.save(rebel2);
    }

    @Test
    void source_should_not_be_able_to_report_target_when_target_already_reported_by_source() {
        ReportDTO reportDTO = new ReportDTO(1, 2);
        ReportUseCase reportUseCase = new ReportUseCase(rebelRepositoryinMemory, reportRules);
        reportUseCase.handle(reportDTO);

        Exception e = assertThrows(Exception.class, () -> reportRules.handle(rebel1, rebel2));
        assertTrue(e.getMessage().contains("rebel already reported"));
    }

    @Test
    void should_throw_IllegalArgumentException_when_source_reports_himself(){
        Exception e = assertThrows(Exception.class, () -> reportRules.handle(rebel1, rebel1));
        assertTrue(e.getMessage().contains("can not report yourself"));
    }
}