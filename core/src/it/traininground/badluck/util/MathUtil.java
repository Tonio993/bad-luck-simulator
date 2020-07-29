package it.traininground.badluck.util;

public class MathUtil {

    private MathUtil() {}

    public static int between(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

}
