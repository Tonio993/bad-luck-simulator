package it.traininground.badluck.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {

    private MathUtil() {}

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
