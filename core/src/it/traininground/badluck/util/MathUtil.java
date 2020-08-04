package it.traininground.badluck.util;

public class MathUtil {

    private MathUtil() {}

    public static int between(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    public static int max(int ...values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("At least one argument must be passed");
        }
        int result = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] > result) {
                result = values[i];
            }
        }
        return result;
    }

    public static int min(int ...values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("At least one argument must be passed");
        }
        int result = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] < result) {
                result = values[i];
            }
        }
        return result;
    }

}
