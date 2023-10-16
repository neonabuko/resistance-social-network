package com.api.resistancesocialnetwork.unit.rules;

import com.api.resistancesocialnetwork.rules.DeleteUserRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteUserRulesTest {

    private final DeleteUserRules rules = new DeleteUserRules();

    @Test
    public void should_throw_exception_when_self_delete() {
        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                rules.handle(1, 1)
        );
        assertTrue(e.getMessage().contains("cannot delete yourself"));
    }
}