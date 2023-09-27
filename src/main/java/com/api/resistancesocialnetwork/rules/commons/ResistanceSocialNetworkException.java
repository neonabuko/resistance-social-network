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
        return stackTrace[3].getClassName();
    }
}

