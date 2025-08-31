package com.code.research.livecoding.tasks;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class CapitalizeFirst {

    private static final Pattern SPLIT = Pattern.compile("[^\\p{L}\\p{N}]+");

    public static String capFirst(String input) {
        if (input == null || input.isBlank()) {
            return "";
        }
        int i = input.offsetByCodePoints(0, 1);
        return new StringBuilder()
                .appendCodePoint(Character.toUpperCase(input.codePointAt(0)))
                .append(input.substring(i).toLowerCase(Locale.ROOT))
                .toString();
    }

    public static void main(String[] args) {
        String input = "alexi,$peter,#@dave";
        String result = SPLIT.splitAsStream(input)
                .filter(s -> !s.isBlank())
                .map(CapitalizeFirst::capFirst)
                .collect(Collectors.joining(" - "));
        log.info("Formatted input: {}", result);
    }
}
