package com.api.resistancesocialnetwork.rules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormatDataTest {

    private final FormatData formatData = new FormatData();

    @Test
    void should_return_undefined_when_string_null() {
        Exception e = assertThrows(IllegalStateException.class, () ->
                formatData.formatString(null, "rebel name")
        );
        assertTrue(e.getMessage().contains("must provide rebel name"));
    }

    @Test
    void should_return_30char_string_when_over_char() {
        String expectedString = "a".repeat(30);
        String actualString = formatData.formatString("a".repeat(31), "rebel name");
        assertEquals(expectedString, actualString);
    }

    @Test
    void should_throw_IllegalStateException_when_coordinate_not_provided() {
        Exception e = assertThrows(IllegalStateException.class,
                () -> formatData.formatCoordinate(null, 180)
        );
        assertTrue(e.getMessage().contains("coordinates required for location"));
    }

    @Test
    void should_return_90_when_latitude_over_90() {
        Double expectedLatitude = 90.0;
        Double actualLatitude = formatData.formatCoordinate(122193.321, 90);
        assertEquals(expectedLatitude, actualLatitude);
    }

    @Test
    void should_return_180_when_longitude_over_180() {
        Double expectedLongitude = 180.0;
        Double actualLongitude = formatData.formatCoordinate(122193.321, 180);
        assertEquals(expectedLongitude, actualLongitude);
    }
}
