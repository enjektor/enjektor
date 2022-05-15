package com.github.enjektor.core.util.booleans;

public class BooleanUtils {

    private BooleanUtils() {
        throw new AssertionError("You can not instantiate any instance from this class.");
    }

    public static int booleanToInteger(boolean bool) {
        return Boolean.compare(bool, false);
    }
}
