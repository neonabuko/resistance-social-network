package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.entity.User;
import com.api.resistancesocialnetwork.facade.DeleteUserFacade;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.UserRepository;
import com.api.resistancesocialnetwork.rules.DeleteUserRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteUserUseCase {
    private final UserRepository repository;
    private final DeleteUserRules rules;

    public DeleteUserUseCase(UserRepository repository, DeleteUserRules rules) {
        this.repository = repository;
        this.rules = rules;
    }

    public void handle(DeleteUserFacade deleteUserFacade, User sourceUser) {
        Integer sourceId = sourceUser.getId();

        Integer targetId = Optional.ofNullable(deleteUserFacade.id()).orElseThrow(
                () -> new ResistanceSocialNetworkException("must provide an id")
        );
        User toDelete = repository.findUserBy(targetId).orElseThrow(
                () -> new ResistanceSocialNetworkException("user not found")
        );
        rules.handle(sourceId, targetId);
        repository.delete(toDelete);
    }
}
