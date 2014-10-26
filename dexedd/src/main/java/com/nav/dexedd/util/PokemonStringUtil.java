package com.nav.dexedd.util;

/**
 * General Pokémon related String methods.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class PokemonStringUtil {

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

    public static String cleanDexText(String text) {
        String cleanText = text.replace("\n", " ");
        return cleanText;
    }
}
