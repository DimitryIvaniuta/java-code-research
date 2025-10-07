package com.code.research.algorithm.colorconverter;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public final class ColorRGBConverter {
    private static final Pattern RGB_PATTERN = Pattern.compile(
            "rgb\\s*\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*\\)",
            Pattern.CASE_INSENSITIVE
    );

    private ColorRGBConverter() {}

    /** Converts a hex color like "#F0ECE4" or "F0ECE4" to "rgb(r, g, b)". */
    public static String hexToRgb(String hex) {
        String h = hex.startsWith("#") ? hex.substring(1) : hex;
        if (h.length() != 6) throw new IllegalArgumentException("Invalid hex color: " + hex);
        int r = Integer.parseInt(h.substring(0, 2), 16);
        int g = Integer.parseInt(h.substring(2, 4), 16);
        int b = Integer.parseInt(h.substring(4, 6), 16);
        return String.format("rgb(%d, %d, %d)", r, g, b);
    }

    /** Converts "rgb(r, g, b)" to a hex color string like "#F0ECE4". */
    public static String rgbToHex(String rgb) {
        Matcher m = RGB_PATTERN.matcher(rgb);
        if (!m.matches()) throw new IllegalArgumentException("Invalid RGB format: " + rgb);
        int r = Integer.parseInt(m.group(1));
        int g = Integer.parseInt(m.group(2));
        int b = Integer.parseInt(m.group(3));
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255)
            throw new IllegalArgumentException("RGB values must be between 0 and 255");
        log.info("Converting rgb to hex #{}{}{}", Integer.toHexString(r), Integer.toHexString(g), Integer.toHexString(b));
        return String.format("#%02X%02X%02X", r, g, b);
    }

    public static void main(String[] args) {
        String hex = "#F0ECE4";
        String rgb = "rgb(240, 236, 228)";
        System.out.println(hexToRgb(hex));    // rgb(240, 236, 228)
        System.out.println(rgbToHex(rgb));    // #F0ECE4
    }
}
