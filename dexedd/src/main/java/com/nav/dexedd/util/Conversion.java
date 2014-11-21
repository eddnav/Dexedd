package com.nav.dexedd.util;

/**
 * Unit conversion utility methods.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class Conversion {

    private static final double INCHES_PER_METER = 39.3701;
    private static final double INCHES_PER_FOOT = 12;
    private static final double POUNDS_PER_KILOGRAM = 2.20462;

    public static String toFeetInches(Double meters) {
        int totalInches = (int) Math.round(meters * INCHES_PER_METER);
        int feet = Double.valueOf(Math.floor(totalInches / INCHES_PER_FOOT)).intValue();
        int inches = Double.valueOf(Math.floor(totalInches % INCHES_PER_FOOT)).intValue();
        if (feet == 0) {
            return inches + "''";
        } else {
            return feet + "'" + inches + "''";
        }
    }

    public static double toPounds(double kilograms) {
        return kilograms * POUNDS_PER_KILOGRAM;
    }
}
