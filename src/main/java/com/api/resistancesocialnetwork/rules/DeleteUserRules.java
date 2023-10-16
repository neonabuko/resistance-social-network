package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DeleteUserRules {

    public void handle(Integer sourceId, Integer targetId) {
        assertNotDeletingHimself(sourceId, targetId);
    }

    private void assertNotDeletingHimself(Integer sourceId, Integer targetId) {
        if (Objects.equals(sourceId, targetId)) throw new ResistanceSocialNetworkException("cannot delete yourself");
    }
}
