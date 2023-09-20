package com.api.resistancesocialnetwork.rules;


import java.util.Optional;

public class GenericRules {
    public String handle(String string) {
        return Optional.ofNullable(string)
                .map( s -> s.substring(0, Math.min(s.length(), 30)) )
                .orElse("undefined");
    }

    public Double handle(Double coord, int bound) {
        return Optional.ofNullable(coord)
                .map( d -> Math.min(d, bound) )
                .map( d -> Math.max(d, -bound) )
                .orElseThrow(
                        () -> new IllegalStateException("all parameters required")
                );
    }

    public Integer handle(Integer integer) {
        int fInteger = Math.min(Optional.ofNullable(integer).orElse(0), 100);
        return Math.max(fInteger, 0);
    }
}