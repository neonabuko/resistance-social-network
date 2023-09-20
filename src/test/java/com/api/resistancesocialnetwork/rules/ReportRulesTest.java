package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.RebelRepository;
import com.api.resistancesocialnetwork.usecase.ReportUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReportRulesTest {

    @Autowired
    private RebelRepository rebelRepository;
    @Autowired
    private ReportRules reportRules;
    @Autowired
    private ReportUseCase reportUseCase;
    private final Rebel rebel1 = new Rebel("cadeirudo", 20, "female");
    private final Rebel rebel2 = new Rebel("linguica", 30, "male");

    @Test
    void should_throw_Exception_when_sourceId_not_provided() {
        Exception e = assertThrows(Exception.class, () ->
                reportRules.handle(null,11)
        );
        assertTrue(e.getMessage().contains("must provide source rebel id"));
    }

    @Test
    void should_throw_Exception_when_targetId_not_provided() {
        rebelRepository.save(rebel1);
        Exception e = assertThrows(Exception.class, () ->
                reportRules.handle(1,null)
        );
        assertTrue(e.getMessage().contains("must provide target rebel id"));
    }
    @Test
    void should_throw_Exception_when_source_rebel_not_found() {
        Exception e = assertThrows(Exception.class, () ->
                reportRules.handle(32,11)
        );
        assertTrue(e.getMessage().contains("source rebel not found"));
    }

    @Test
    void should_throw_Exception_when_target_rebel_not_found() {
        rebelRepository.save(rebel1);
        Exception e = assertThrows(Exception.class, () ->
                reportRules.handle(rebel1.getId(),46)
        );
        assertTrue(e.getMessage().contains("target rebel not found"));
    }

    @Test
    void source_should_not_be_able_to_report_target_when_target_already_reported() {
        rebelRepository.save(rebel2);
        rebel1.getReportedRebels().add(rebel2.getId());
        rebelRepository.save(rebel1);

        Exception e = assertThrows(Exception.class, () ->
                reportUseCase.handle(rebel1.getId(),rebel2.getId()));
        assertTrue(e.getMessage().contains("rebel already reported"));
    }
}