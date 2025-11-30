package com.code.research.algorithm.tasks;

import lombok.extern.slf4j.Slf4j;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Slf4j
public class StreamStringSumIntegers {

    // Keep only digits and +/-; drop everything else
    public static final Pattern STRIP_NON_DIGIT_SIGN = Pattern.compile("[^\\d+\\-]+");
    // First digit run
    private static final Pattern DIGITS = Pattern.compile("\\d+");

    public static Integer sumWithIntStream(String intString) {
        if (intString == null || intString.isBlank()) return 0;
        return Stream.of(intString.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .mapToInt(StreamStringSumIntegers::parseSignedIntLoose)
                .sum();
    }

    private static int parseSignedIntLoose(String token) {
        if (token == null || token.isBlank()) return 0;
        String normalizedToken = STRIP_NON_DIGIT_SIGN.matcher(token).replaceAll("");
        int i = 0, sign = 1;
        while (i < normalizedToken.length()
                && (normalizedToken.charAt(i) == '+' || normalizedToken.charAt(i) == '-') && sign > 0) {
            if (normalizedToken.charAt(i) == '-') sign = -sign;
            i++;
        }
        // Extract the first contiguous digits after the sign
        Matcher m = DIGITS.matcher(normalizedToken);
        if (!m.find()) {
            return 0;
        }
        int val = Integer.parseInt(m.group());
        return sign * val;
    }

    public static void main(String[] args) {
        String intStr = args.length > 0 ? args[0] : "10 , 20 , 3 0 , ---5, --a#3%, +1, -2";
        log.info("String sum: {}", sumWithIntStream(intStr)); // 51
    }
}
