package com.code.research.string;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Utility class for processing mixed text:
 * - parsing & summarizing integers,
 * - reversing the text,
 * - shifting each character to the next code-point.
 */
@Slf4j
public final class StringProcessor {

    // Delimiter: any run of characters that are NOT digits or minus-sign
    private static final Pattern NUMBER_DELIMITER = Pattern.compile("[^0-9-]+");

    private StringProcessor() {
        //
    }

    /**
     * Parses all integers out of the input, computes their sum,
     * and counts how often each integer appears.
     */
    public static NumberStats analyzeNumbers(String input) {
        NumberStats numberStats = new NumberStats();
        try (Scanner scanner = new Scanner(input).useDelimiter(NUMBER_DELIMITER)) {
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    int number = scanner.nextInt();
                    numberStats = numberStats.withAdded(number);

                } else {
                    scanner.next();
                }
            }
        }
        return numberStats;
    }

    /**
     * Returns the input string reversed.
     */
    public static String reverse(String input) {
        return new StringBuilder(input)
                .reverse()
                .toString();
    }

    public static String shiftCharacters(String input) {
        StringBuilder stringBuilder = new StringBuilder(input);
        stringBuilder.codePoints()
                .map(cp -> cp + 1)
                .forEach(stringBuilder::appendCodePoint);
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        String input = "apple 12 orange 7 banana 12 grape 5 melon 7 3";

        // 1) Number analysis
        NumberStats stats = analyzeNumbers(input);
        log.info("Original: {}", input);
        log.info("Sum of numbers: {}", stats.getSum());
        log.info("Appearances:");
        stats.getCounts().forEach((num, cnt) ->
                log.info(String.format("  %d -> %d time%s%n", num, cnt, (cnt > 1 ? "s" : "")))
        );

        // 2) Reverse
        log.info("Reversed: {}", reverse(input));

        // 3) Shift characters
        log.info("Shifted:  {}", shiftCharacters(input));
    }

}
