package com.api.resistancesocialnetwork.formatters;

import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import org.springframework.stereotype.Component;

@Component
public class FormatData {

    public String formatString(String string, String paramName) throws ResistanceSocialNetworkException {
        if (paramName == null) throw new ResistanceSocialNetworkException("must provide name of parameter");
        if (string == null) throw new ResistanceSocialNetworkException("must provide " + paramName);

        int maxLength = Math.min(string.length(), 30);

        return string.substring(0, maxLength);
    }

    public Double formatCoordinate(Double coord, Integer absoluteLimit) throws ResistanceSocialNetworkException {
        if (absoluteLimit == null) throw new ResistanceSocialNetworkException("must provide absolute limit of coordinate");
        if (coord == null) throw new ResistanceSocialNetworkException("must provide coordinates");

        double min = Math.min(coord, absoluteLimit);

        return Math.max(min, -absoluteLimit);
    }

    public Integer formatInteger(Integer integer, String paramName) throws ResistanceSocialNetworkException {
        if (paramName == null) throw new ResistanceSocialNetworkException("must provide name of parameter");
        if (integer == null) throw new ResistanceSocialNetworkException("must provide " + paramName);

        int min = Math.min(integer, 100);

        return Math.max(min, 0);
    }
}
