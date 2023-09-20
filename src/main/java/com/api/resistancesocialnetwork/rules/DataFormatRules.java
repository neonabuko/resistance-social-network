package com.api.resistancesocialnetwork.rules;


import com.api.resistancesocialnetwork.model.Location;
import com.api.resistancesocialnetwork.model.Rebel;

import java.util.Optional;

public class DataFormatRules {
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

    public Rebel handle(Rebel rebel) {
        Optional<Rebel> optionalRebel = Optional.ofNullable(rebel);
        if (optionalRebel.isEmpty())
            return optionalRebel.orElse(new Rebel("undefined", 18, "undefined"));

        rebel.setStats(
                handle(rebel.getName()),
                handle(rebel.getAge()),
                handle(rebel.getGender())
        );

        return rebel;
    }

    public Location handle(Location location) {
        Optional<Location> optionalLocation = Optional.ofNullable(location);
        if (optionalLocation.isEmpty()) throw new IllegalStateException("all parameters required for location");

        location.setNewLocation(handle(location.getLatitude(), 90),
                                handle(location.getLongitude(), 180),
                                handle(location.getBase()));
        return location;
    }
}