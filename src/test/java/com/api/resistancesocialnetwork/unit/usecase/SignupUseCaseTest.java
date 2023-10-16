package com.api.resistancesocialnetwork.unit.usecase;

import com.api.resistancesocialnetwork.enums.UserRole;
import com.api.resistancesocialnetwork.facade.SignupFacade;
import com.api.resistancesocialnetwork.repository.repositoriesinmemory.UserRepositoryInMemory;
import com.api.resistancesocialnetwork.rules.SignupRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import com.api.resistancesocialnetwork.usecase.SignupUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignupUseCaseTest {
    private final UserRepositoryInMemory userRepository = new UserRepositoryInMemory();
    private final SignupRules signupRules = new SignupRules();
    private final SignupUseCase signupUseCase = new SignupUseCase(userRepository, signupRules);


    @Test
    @DisplayName("should save new user if data valid")
    void should_save_user_if_data_valid() {
        SignupFacade signupFacade = new SignupFacade("username", "123", UserRole.ADMIN);
        signupUseCase.handle(signupFacade);
        assertTrue(userRepository.findUserBy("username").isPresent());
    }

    @Test
    @DisplayName("should throw exception when username taken")
    void should_throw_exception_when_username_taken() {
        SignupFacade signupFacade = new SignupFacade("taken", "123", UserRole.ADMIN);
        signupUseCase.handle(signupFacade);
        Exception e = assertThrows(ResistanceSocialNetworkException.class,
                () -> signupUseCase.handle(signupFacade)
        );

        assertTrue(e.getMessage().contains("username 'taken' already taken"));
    }
}