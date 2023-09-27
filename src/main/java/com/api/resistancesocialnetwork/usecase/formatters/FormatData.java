package com.api.resistancesocialnetwork.usecase.formatters;

import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import org.springframework.stereotype.Component;

@Component
public class FormatData {

    public String formatString(String string, Integer maxLength, String paramName) throws ResistanceSocialNetworkException {
        if (paramName == null) throw new ResistanceSocialNetworkException("must provide name of parameter");
        if (string == null) throw new ResistanceSocialNetworkException("must provide " + paramName);

        int min = Math.min(string.length(), maxLength);

        return string.substring(0, min);
    }

    public Double formatCoordinate(Double coord, Integer symmetricLimit) throws ResistanceSocialNetworkException {
        if (symmetricLimit == null) throw new ResistanceSocialNetworkException("must provide symmetric limit of coordinate");
        if (coord == null) throw new ResistanceSocialNetworkException("must provide coordinates");

        double min = Math.min(coord, symmetricLimit);

        return Math.max(min, -symmetricLimit);
    }

    public Integer formatInteger(Integer integer, Integer max, String paramName) throws ResistanceSocialNetworkException {
        if (paramName == null) throw new ResistanceSocialNetworkException("must provide name of parameter");
        if (integer == null) throw new ResistanceSocialNetworkException("must provide " + paramName);

        int min = Math.min(integer, max);

        return Math.max(min, 0);
    }
}
