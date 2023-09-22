package com.api.resistancesocialnetwork.rules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataFormatRulesTest {

    @Test
    void should_return_undefined_when_string_null() {
        DataFormatRules dataFormatRules = new DataFormatRules();
        String actualString = dataFormatRules.handle((String) null);
        assertEquals("undefined", actualString);
    }

    @Test
    void should_return_30char_string_when_over_char() {
        DataFormatRules dataFormatRules = new DataFormatRules();
        String expectedString = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String actualString = dataFormatRules.handle("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertEquals(expectedString, actualString);
    }

    @Test
    void should_throw_IllegalStateException_when_coordinates_not_filled() {
        DataFormatRules dataFormatRules = new DataFormatRules();
        Exception e = assertThrows(IllegalStateException.class, () ->
                dataFormatRules.handle(null, 180)
        );
        assertTrue(e.getMessage().contains("all parameters required"));
    }

    @Test
    void should_return_90_when_latitude_over_90() {
        DataFormatRules dataFormatRules = new DataFormatRules();
        Double expectedLatitude = 90.0;
        Double actualLatitude = dataFormatRules.handle(122193.321, 90);
        assertEquals(expectedLatitude, actualLatitude);
    }

    @Test
    void should_return_180_when_longitude_over_180() {
        DataFormatRules dataFormatRules = new DataFormatRules();
        Double expectedLongitude = 180.0;
        Double actualLongitude = dataFormatRules.handle(122193.321, 180);
        assertEquals(expectedLongitude, actualLongitude);
    }
}
