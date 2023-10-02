package com.api.resistancesocialnetwork.rules;

import com.api.resistancesocialnetwork.usecase.formatters.FormatData;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormatDataTest {

    private final FormatData formatData = new FormatData();

    @Test
    @DisplayName("should throw exception when string null")
    void should_throw_ResistanceSocialNetworkException_when_string_null() {
        Exception e = assertThrows(ResistanceSocialNetworkException.class, () ->
                formatData.formatString(null, 30, "rebel name")
        );
        assertTrue(e.getMessage().contains("must provide rebel name"));
    }

    @Test
    @DisplayName("should reduce string length to limit")
    void should_reduce_string_when_string_over_char_limit() throws ResistanceSocialNetworkException {
        String actualString = formatData.formatString("a".repeat(31), 30, "rebel name");
        String expectedString = "a".repeat(30);
        assertEquals(expectedString, actualString);
    }

    @Test
    @DisplayName("should throw exception when coordinate null")
    void should_throw_ResistanceSocialNetworkException_when_coordinate_null() {
        Exception e = assertThrows(ResistanceSocialNetworkException.class,
                () -> formatData.formatCoordinate(null, 180)
        );
        assertTrue(e.getMessage().contains("must provide coordinates"));
    }

    @Test
    @DisplayName("should output double inside symmetric limit")
        // symmetricLimit = 90 => formatCoordinate(238.7) = 90.0 | formatCoordinate(-372.4426) = -90.0
    void should_round_coordinate_to_symmetric_limit() throws ResistanceSocialNetworkException {
        Integer symmetricLimit = 90;

        Double actual_positive_output = formatData.formatCoordinate(122193.321, symmetricLimit);
        Double expected_positive_output = 90.0;

        Double actual_negative_output = formatData.formatCoordinate(-122193.321, symmetricLimit);
        Double expected_negative_output = -90.0;

        assertEquals(expected_positive_output, actual_positive_output);
        assertEquals(expected_negative_output, actual_negative_output);
    }

    @Test
    @DisplayName("should output integer inside limit")
    void should_round_integer_to_limit() {
        Integer actualInteger = formatData.formatInteger(2348, 100, "");
        Integer expectedInteger = 100;
        assertEquals(expectedInteger, actualInteger);
    }
}
