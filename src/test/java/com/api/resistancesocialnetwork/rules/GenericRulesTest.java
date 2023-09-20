package com.api.resistancesocialnetwork.rules;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GenericRulesTest {

    @Test
    void should_return_undefined_when_string_null() {
        GenericRules genericRules = new GenericRules();
        String actualString = genericRules.handle((String) null);
        assertEquals("undefined", actualString);
    }

    @Test
    void should_return_30char_string_when_over_char() {
        GenericRules genericRules = new GenericRules();
        String expectedString = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String actualString = genericRules.handle("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertEquals(expectedString, actualString);
    }

    @Test
    void should_throw_IllegalStateException_when_coordinates_not_filled() {
        GenericRules genericRules = new GenericRules();
        Exception e = assertThrows(IllegalStateException.class, () ->
                genericRules.handle(null, 180)
        );
        assertTrue(e.getMessage().contains("all parameters required"));
    }

    @Test
    void should_return_90_when_latitude_over_90() {
        GenericRules genericRules = new GenericRules();
        Double expectedLatitude = 90.0;
        Double actualLatitude = genericRules.handle(122193.321, 90);
        assertEquals(expectedLatitude, actualLatitude);
    }

    @Test
    void should_return_180_when_longitude_over_180() {
        GenericRules genericRules = new GenericRules();
        Double expectedLongitude = 180.0;
        Double actualLongitude = genericRules.handle(122193.321, 180);
        assertEquals(expectedLongitude, actualLongitude);
    }
}
