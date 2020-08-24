package it.traininground.badluck.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {

    private MathUtil() {}
    
    public static <T extends Comparable<T>> T min(T v1, T v2) {
    	return v1.compareTo(v2) <= 0 ? v1 : v2;
    }

    public static <T extends Comparable<T>> T max(T v1, T v2) {
    	return v1.compareTo(v2) >= 0 ? v1 : v2;
    }
    
    public static int between(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    public static float round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

}
