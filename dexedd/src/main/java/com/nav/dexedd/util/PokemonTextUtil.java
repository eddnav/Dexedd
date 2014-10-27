package com.nav.dexedd.util;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * General Pokémon related Text methods.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class PokemonTextUtil {

    /**
     * Processable pattern.
     */
    private static final String PROCESSABLE_DEX_TEXT_PATTERN = "\\[(.*?)\\]\\{(.+?):(.+?)\\}";

    private static final String PROCESSABLE_TYPE = "type";
    /* private static final String PROCESSABLE_MECHANIC = "mechanic";
    private static final String PROCESSABLE_MOVE = "move";
    private static final String PROCESSABLE_ITEM = "item"; */ // Unused for now

    /**
     * Formats dex numbers in a visually familiar manner.
     *
     * @param number The dex number to be formatted.
     *
     * @return The formatted dex number.
     */
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
        return text.replace("\n", " ");
    }

    /**
     * Processes Pokémon text such as ability entries which use special processable patterns for formatting
     * (such as type, mechanics and moves), ie: [badly poisoned]{mechanic:badly-poisoned}.
     *
     * @param context           Application context.
     * @param unprocessedString String to be processed.
     *
     * @return Builder with special formatting ready to be set to a TextView.
     */
    public static SpannableStringBuilder processDexText(Context context, String unprocessedString) {
        int renderGroup = 1;
        Pattern pattern = Pattern.compile(PROCESSABLE_DEX_TEXT_PATTERN);
        Matcher matcher = pattern.matcher(unprocessedString);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        int head = 0;
        while (matcher.find()) {
            renderGroup = 1;
            builder.append(unprocessedString.substring(head, matcher.start()));
            if (matcher.group(1).equals("")) {
                renderGroup = 3;
            }
            String processableExtract = matcher.group(renderGroup);

            // Set type formatting for the found matches
            if (matcher.group(2).equals(PROCESSABLE_TYPE)) {
                String properProcessableExtract =
                        processableExtract.substring(0, 1).toUpperCase() + processableExtract.substring(1);
                builder.append(properProcessableExtract);
                final StyleSpan bold = new StyleSpan(android.graphics.Typeface.BOLD);
                final ForegroundColorSpan color =
                        new ForegroundColorSpan(context.getResources().getColor(TypeUtil.getTypeColorRes(
                                TypeUtil.Type.getTypeByName(processableExtract))));
                int start = builder.length() - matcher.group(renderGroup).length();
                int end = builder.length();
                builder.setSpan(bold, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.setSpan(color, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                builder.append(processableExtract);
            }
            head = matcher.end();
        }
        builder.append(unprocessedString.substring(head, unprocessedString.length()));
        return builder;
    }

}
