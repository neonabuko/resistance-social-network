package com.api.resistancesocialnetwork.unit.usecase;

import com.api.resistancesocialnetwork.entity.ResistanceUser;
import com.api.resistancesocialnetwork.enums.UserRole;
import com.api.resistancesocialnetwork.facade.DeleteUserFacade;
import com.api.resistancesocialnetwork.repository.repositoriesinmemory.ResistanceUserRepositoryInMemory;
import com.api.resistancesocialnetwork.rules.DeleteUserRules;
import com.api.resistancesocialnetwork.usecase.DeleteUserUseCase;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

class DeleteUserUseCaseTest {
    private final ResistanceUserRepositoryInMemory repository = new ResistanceUserRepositoryInMemory();
    private final DeleteUserUseCase delete = new DeleteUserUseCase(repository, new DeleteUserRules());

    @Test
    public void shouldDeleteUser() {
        ResistanceUser sourceUser = new ResistanceUser("sourceUser", "123", UserRole.ADMIN);
        sourceUser.setId(1);
        ResistanceUser targetUser = new ResistanceUser("targetUser", "123", UserRole.USER);
        targetUser.setId(2);
        repository.saveAllInMem(List.of(sourceUser, targetUser));

        DeleteUserFacade facade = new DeleteUserFacade(targetUser.getId());

        delete.handle(facade, sourceUser);

        assertFalse(repository.findUserBy(targetUser.getId()).isPresent());
    }
}