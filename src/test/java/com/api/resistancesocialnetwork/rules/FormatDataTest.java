package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.formatters.FormatData;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormatDataTest {

    private final FormatData formatData = new FormatData();

    @Test
    void should_throw_ResistanceSocialNetworkException_when_string_null() {
        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                formatData.formatString(null, "rebel name")
        );
        assertTrue(e.getMessage().contains("must provide rebel name"));
    }

    @Test
    @DisplayName("should reduce to 30 characters string when string has more than 30 characters")
    void should_reduce_to_30char_string_when_string_over_30_char() throws ResistanceSocialNetworkException {
        String expectedString = "a".repeat(30);
        String actualString = formatData.formatString("a".repeat(31), "rebel name");
        assertEquals(expectedString, actualString);
    }

    @Test
    void should_throw_ResistanceSocialNetworkException_when_coordinate_null() {
        Exception e = assertThrows(ResistanceSocialNetworkException.class,
                () -> formatData.formatCoordinate(null, 180)
        );
        assertTrue(e.getMessage().contains("must provide coordinates"));
    }

    @Test
    @DisplayName("should round a coordinate to provided absolute limit")
        // absoluteLimit = 90  =>  formatCoordinate(238) = 90 | formatCoordinate(-372) = -90
    void should_round_coordinate_to_absolute_limit_when_under_or_over() throws ResistanceSocialNetworkException {
        Double down_rounded_output = formatData.formatCoordinate(122193.321, 90);
        Double expected_down_rounded_output = 90.0;
        Double up_rounded_output = formatData.formatCoordinate(122193.321, 90);
        Double expected_up_rounded_output = 90.0;

        assertEquals(expected_up_rounded_output, up_rounded_output);
        assertEquals(expected_down_rounded_output, down_rounded_output);
    }

    @Test
    @DisplayName("should round an integer to 100 when over 100")
    void should_round_integer_to_100_when_over_100() {
        Integer expectedInteger = 100;
        Integer actualInteger = formatData.formatInteger(2348, "");
        assertEquals(expectedInteger, actualInteger);
    }

    @Test
    @DisplayName("should round an integer to 0 when under 0")
    void should_round_integer_to_0_when_under_0() {
        Integer expectedInteger = 0;
        Integer actualInteger = formatData.formatInteger(-2348, "");
        assertEquals(expectedInteger, actualInteger);
    }
}
