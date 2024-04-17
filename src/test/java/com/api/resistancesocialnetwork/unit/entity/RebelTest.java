package com.api.resistancesocialnetwork.unit.entity;

import com.api.resistancesocialnetwork.entity.Rebel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RebelTest {

    @Test
    @DisplayName("Should set rebel stats correctly.")
    void should_set_rebel_stats() {
        var luke = new Rebel();
        luke.setStats("luke", 24, "male");

        assertEquals(luke.getName(), "luke");
        assertEquals(luke.getAge(), 24);
        assertEquals(luke.getGender(), "male");
    }
}