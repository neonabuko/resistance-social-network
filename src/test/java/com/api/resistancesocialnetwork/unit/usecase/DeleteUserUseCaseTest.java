package com.api.resistancesocialnetwork.unit.usecase;

import com.api.resistancesocialnetwork.entity.User;
import com.api.resistancesocialnetwork.enums.UserRole;
import com.api.resistancesocialnetwork.facade.DeleteUserFacade;
import com.api.resistancesocialnetwork.repository.repositoriesinmemory.UserRepositoryInMemory;
import com.api.resistancesocialnetwork.rules.DeleteUserRules;
import com.api.resistancesocialnetwork.usecase.DeleteUserUseCase;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

class DeleteUserUseCaseTest {
    private final UserRepositoryInMemory repository = new UserRepositoryInMemory();
    private final DeleteUserUseCase delete = new DeleteUserUseCase(repository, new DeleteUserRules());

    @Test
    public void shouldDeleteUser() {
        User sourceUser = new User("sourceUser", "123", UserRole.ADMIN);
        sourceUser.setId(1);
        User targetUser = new User("targetUser", "123", UserRole.USER);
        targetUser.setId(2);
        repository.saveAllInMem(List.of(sourceUser, targetUser));

        DeleteUserFacade facade = new DeleteUserFacade(targetUser.getId());

        delete.handle(facade, sourceUser);

        assertFalse(repository.findUserBy(targetUser.getId()).isPresent());
    }
}