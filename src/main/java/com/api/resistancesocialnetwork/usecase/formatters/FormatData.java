package com.api.resistancesocialnetwork.usecase.formatters;

import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import org.springframework.stereotype.Component;

@Component
public class FormatData {

    public String formatString(String string, Integer maxLength, String paramName) throws ResistanceException {
        if (paramName == null) throw new ResistanceException("must provide name of parameter");
        if (string == null) throw new ResistanceException("must provide " + paramName);

        int min = Math.min(string.length(), maxLength);

        return string.substring(0, min);
    }

    public Double formatCoordinate(Double coord, Integer symmetricLimit) throws ResistanceException {
        if (symmetricLimit == null) throw new ResistanceException("must provide symmetric limit of coordinate");
        if (coord == null) throw new ResistanceException("must provide coordinates");

        double min = Math.min(coord, symmetricLimit);

        return Math.max(min, -symmetricLimit);
    }

    public Integer formatInteger(Integer integer, Integer max, String paramName) throws ResistanceException {
        if (paramName == null) throw new ResistanceException("must provide name of parameter");
        if (integer == null) throw new ResistanceException("must provide " + paramName);

        int min = Math.min(integer, max);

        return Math.max(min, 0);
    }
}
