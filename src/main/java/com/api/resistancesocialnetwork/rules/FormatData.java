package com.api.resistancesocialnetwork.rules;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FormatData {

    public String formatString(String string, String paramName) {
        return Optional.ofNullable(string).map(s -> s.substring(0, Math.min(s.length(), 30))).orElseThrow(
                () -> new IllegalStateException("must provide " + paramName)
        );
    }

    public Double formatCoordinate(Double coord, int coordAbsoluteLimit) {
        return Optional.ofNullable(coord).map(d -> Math.min(d, coordAbsoluteLimit)).map(d -> Math.max(d, -coordAbsoluteLimit)).orElseThrow(
                () -> new IllegalStateException("coordinates required for location")
        );
    }

    public Integer formatInteger(Integer integer, String paramName) {
        int output = Math.min(Optional.ofNullable(integer).orElseThrow(
                () -> new IllegalStateException("must provide " + paramName))
                , 100);
        return Math.max(output, 0);
    }
}
