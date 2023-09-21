package com.api.resistancesocialnetwork.repositories;

import com.api.resistancesocialnetwork.rules.ReportRules;
import com.api.resistancesocialnetwork.usecase.ReportUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class RepositoryInterfaceTest {

    private RebelRepositoryInMem rebelRepoInMem;

    @BeforeEach
    void setUp() {
        this.rebelRepoInMem = new RebelRepositoryInMem();
    }

    @Test
    void should() throws Exception {
        ReportUseCase reportUseCase = new ReportUseCase(rebelRepoInMem, new ReportRules(rebelRepoInMem));
        reportUseCase.handle(0, 1);
    }
}