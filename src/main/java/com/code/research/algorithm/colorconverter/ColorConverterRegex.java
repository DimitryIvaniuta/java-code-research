package com.code.research.algorithm.colorconverter;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ColorConverterRegex {
    static Pattern pattern = Pattern.compile("#?([0-9a-fA-F]{2})([0-9a-fA-F]{2})([0-9a-fA-F]{2})");

    record Color(int r, int g, int b) {
    }

    private static Color hexToRgb(String hex) {
        Matcher matcher = pattern.matcher(hex);
        if (matcher.find()) {
            return new Color(Integer.parseInt(matcher.group(1), 16),
                    Integer.parseInt(matcher.group(2), 16),
                    Integer.parseInt(matcher.group(3), 16));
        }
        return null;
    }

    public static void main(String[] args) {
        String hex = "#F0ECE4";
        log.info("RGB color values: {}", hexToRgb(hex));
    }
}
