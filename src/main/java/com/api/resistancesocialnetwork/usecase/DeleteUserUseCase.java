package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.entity.ResistanceUser;
import com.api.resistancesocialnetwork.facade.DeleteUserFacade;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.ResistanceUserRepository;
import com.api.resistancesocialnetwork.rules.DeleteUserRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteUserUseCase {
    private final ResistanceUserRepository repository;
    private final DeleteUserRules rules;

    public DeleteUserUseCase(ResistanceUserRepository repository, DeleteUserRules rules) {
        this.repository = repository;
        this.rules = rules;
    }

    public void handle(DeleteUserFacade deleteUserFacade, ResistanceUser sourceUser) {
        Integer sourceId = sourceUser.getId();

        Integer targetId = Optional.ofNullable(deleteUserFacade.id()).orElseThrow(
                () -> new ResistanceException("must provide an id")
        );
        ResistanceUser toDelete = repository.findUserBy(targetId).orElseThrow(
                () -> new ResistanceException("user not found")
        );
        rules.handle(sourceId, targetId);
        repository.delete(toDelete);
    }
}
