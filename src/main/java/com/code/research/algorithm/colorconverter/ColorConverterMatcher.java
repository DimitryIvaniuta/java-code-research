package com.code.research.algorithm.colorconverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorConverterMatcher {
    public static int POINTER_SIZE = 255;
    // Matches "#RGB" or "#RRGGBB" (with or without leading '#'), capturing either 3 or 6 hex digits
    private static final Pattern HEX_PATTERN = Pattern.compile(
            "^#?(?:"
                    + "([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])"   // groups 1–3: single hex digits
                    + "|"
                    + "([A-Fa-f0-9]{2})([A-Fa-f0-9]{2})([A-Fa-f0-9]{2})" // groups 4–6: full pairs
                    + ")$"
    );

    private ColorConverterMatcher() {}

    /**
     * Converts a hex color ("#RGB" or "#RRGGBB") to "rgb(r, g, b)".
     * Does NOT use substring; uses regex groups and concatenation.
     */
    public static String hexToRgb(String hex) {
        Matcher m = HEX_PATTERN.matcher(hex.trim());
        if (!m.matches()) {
            throw new IllegalArgumentException("Invalid hex color: " + hex);
        }
        // Determine red component
        String rHex = (m.group(1) != null)
                ? m.group(1) + m.group(1)   // expand single digit to pair
                : m.group(4);               // already a pair
        // Determine green component
        String gHex = (m.group(2) != null)
                ? m.group(2) + m.group(2)
                : m.group(5);
        // Determine blue component
        String bHex = (m.group(3) != null)
                ? m.group(3) + m.group(3)
                : m.group(6);

        int r = Integer.parseInt(rHex, 16);
        int g = Integer.parseInt(gHex, 16);
        int b = Integer.parseInt(bHex, 16);

        return String.format("rgb(%d, %d, %d)", r, g, b);
    }

    // Demo
    public static void main(String[] args) {
        System.out.println(hexToRgb("#F0ECE4")); // rgb(240, 236, 228)
        System.out.println(hexToRgb("FFF"));     // rgb(255, 255, 255)
        System.out.println(hexToRgb("#ABC"));    // rgb(170, 187, 204)
        String hex = "#F0ECE4";
        System.out.println("splt0: "+hex.substring(0,2));    // rgb(170, 187, 204)
        System.out.println("splt1: "+hex.substring(1,3));    // rgb(170, 187, 204)
        System.out.println("splt2: "+hex.substring(3,5));    // rgb(170, 187, 204)
        System.out.println("splt3: "+hex.substring(5,hex.length()));    // rgb(170, 187, 204)
    }
}
