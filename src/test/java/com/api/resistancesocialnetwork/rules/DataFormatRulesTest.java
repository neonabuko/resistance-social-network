package com.api.resistancesocialnetwork.rules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataFormatRulesTest {

    @Test
    void should_return_undefined_when_string_null() {
        DataFormatRules dataFormatRules = new DataFormatRules();
        String actualString = dataFormatRules.formatString(null);
        assertEquals("undefined", actualString);
    }

    @Test
    void should_return_30char_string_when_over_char() {
        DataFormatRules dataFormatRules = new DataFormatRules();
        String expectedString = "a".repeat(30);
        String actualString = dataFormatRules.formatString("a".repeat(31));
        assertEquals(expectedString, actualString);
    }

    @Test
    void should_throw_IllegalStateException_when_coordinate_not_provided() {
        DataFormatRules dataFormatRules = new DataFormatRules();
        Exception e = assertThrows(IllegalStateException.class,
                () -> dataFormatRules.formatCoordinate(null, 180)
        );
        assertTrue(e.getMessage().contains("coordinates required for location"));
    }

    @Test
    void should_return_90_when_latitude_over_90() {
        DataFormatRules dataFormatRules = new DataFormatRules();
        Double expectedLatitude = 90.0;
        Double actualLatitude = dataFormatRules.formatCoordinate(122193.321, 90);
        assertEquals(expectedLatitude, actualLatitude);
    }

    @Test
    void should_return_180_when_longitude_over_180() {
        DataFormatRules dataFormatRules = new DataFormatRules();
        Double expectedLongitude = 180.0;
        Double actualLongitude = dataFormatRules.formatCoordinate(122193.321, 180);
        assertEquals(expectedLongitude, actualLongitude);
    }
}
