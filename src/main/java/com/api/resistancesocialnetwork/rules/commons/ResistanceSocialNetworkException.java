package com.api.resistancesocialnetwork.rules.commons;


public class ResistanceSocialNetworkException extends RuntimeException {
    public ResistanceSocialNetworkException(String s) {
        super(getCallingClassName() + ": " + s);
    }
    private static String getCallingClassName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length < 4) {
            return "UnknownClass";
        }
        assert stackTrace[3].getFileName() != null;
        return stackTrace[3].getFileName().replace(".java", "");
    }
}

