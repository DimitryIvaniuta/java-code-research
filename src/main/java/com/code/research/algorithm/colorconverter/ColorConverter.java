package com.code.research.algorithm.colorconverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class ColorConverter {

    public record Color(int red, int green, int blue) {}

    // Matches "#RGB" or "#RRGGBB" (with or without leading '#')
    private static final Pattern HEX_PATTERN = Pattern.compile("^#?([A-Fa-f0-9]{3}|[A-Fa-f0-9]{6})$");

    public ColorConverter() {
    }

    /**
     * Converts a hex color ("#RGB" or "#RRGGBB") to "rgb(r, g, b)".
     * Expands 3-digit form (e.g. "FAB" → "FF A A BB").
     */
    public static Color hexToRGB(String color) {
        Matcher matcher = HEX_PATTERN.matcher(color);
        if(!matcher.matches()){
            return null;
        }
//        String rgb = matcher.group(1);
        return new Color(0, 0, 0);
    }
}
