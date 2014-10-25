package com.nav.dexedd.util;

/**
 * General Dex related String methods.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class DexStringUtil {

    public static String getFormattedDexNumber(Integer number) {
        if (number < 10) {
            return "#00" + number;
        } else {
            if (number < 100) {
                return "#0" + number;
            } else {
                return "#" + number;
            }
        }
    }
}
