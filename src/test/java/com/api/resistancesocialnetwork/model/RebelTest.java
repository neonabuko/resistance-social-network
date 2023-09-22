package com.api.resistancesocialnetwork.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RebelTest {

    @Test
    void rebel_id_should_not_be_null_upon_creation() {
        Rebel rebel = new Rebel("matheus", 28, "male");
        assertNotNull(rebel.getId());
    }
}