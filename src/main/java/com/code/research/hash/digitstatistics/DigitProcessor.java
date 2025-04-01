package com.code.research.hash.digitstatistics;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.stream.Collectors;

/**
 * DigitProcessor provides a method to compute digit statistics from a given number.
 * It computes both the overall sum of the digits and a frequency map, where the counts
 * are represented in a generic numeric type. A conversion function is provided to transform
 * the raw count (a Long) into the desired type.
 */
public class DigitProcessor {

    private DigitProcessor() {
        //
    }

    /**
     * Computes the overall sum of digits in the given number and builds a frequency map,
     * where each key is a digit and the value is its frequency count converted to type T.
     *
     * <p>This method first converts the number to its string representation, then processes
     * the digits using the Java Stream API. The raw counts are computed as Long values and
     * then converted using the provided converter function.
     *
     * @param number the input number.
     * @param adder  a BinaryOperator to sum two counts (e.g. Integer::sum, Long::sum, etc.).
     * @param <T>    the numeric type for frequency counts.
     * @return a DigitStatisticsRecord containing the overall sum of digits and a frequency map with counts of type T.
     */
    public static <T extends Number> DigitStatisticsRecord<T> computeDigits(Long number,
                                                                            T identity,
                                                                            BinaryOperator<T> adder) {
        String numberStr = String.valueOf(number);
        int sum = numberStr.chars().map(c -> c - '0').sum();

        // Compute overall sum of digits (as int).
        Map<Integer, T> frequencies = numberStr.chars().map(c -> c - '0')
                .boxed()
                .collect(
                        Collectors.toMap(Function.identity(),
                                digit -> identity,
                                adder)
                );
        return new DigitStatisticsRecord<>(sum, frequencies);
    }

    /**
     * Computes the overall sum of digits in the given number and builds a frequency map using
     * the provided FrequencyCollector to determine the numeric type for frequency counts.
     *
     * <p>The method converts the input number to its string representation, then processes each character
     * to obtain its numeric value. It computes the sum of the digits and uses a stream to collect the frequencies
     * using the supplied FrequencyCollector.
     *
     * @param number    the input number.
     * @param collector a FrequencyCollector that determines how to accumulate frequency counts.
     * @param <T>       the numeric type for frequency counts.
     * @return a DigitStatisticsRecord containing the sum of digits and the frequency map.
     */
    public static <T extends Number> DigitStatisticsRecord<T> computeDigitsWithCollector(
            Long number,
            FrequencyCollector<Integer, T> collector
    ) {
        String numberStr = String.valueOf(number);
        // Compute the sum of digits.
        int sum = numberStr.chars().map(c -> c - '0').sum();
        Map<Integer, T> frequencyMap = numberStr.chars()
                .map(c -> c - '0') // Convert each char to its digit.
                .boxed() // Convert int to Integer.
                .collect(collector);

        return new DigitStatisticsRecord<>(sum, frequencyMap);
    }

    /**
     * Computes the overall sum of digits in the given number and builds a frequency map where each key is a digit
     * and the value is its frequency count converted to type T.
     *
     * <p>The method converts the input number to its string representation, then processes the digits by mapping
     * each character to its numeric value. It then groups the digits and counts their occurrences. Finally, it
     * converts each raw count (Long) into the desired numeric type T using the provided converter function.
     *
     * @param number    the input number to process
     * @param converter a function that converts a raw frequency count (Long) to the desired type T
     * @param <T>       the numeric type used for frequency counts
     * @return a {@code DigitStatisticsRecord<T>} containing the overall sum of digits and the frequency map
     */
    public static <T extends Number> DigitStatisticsRecord<T> computeDigitsWithConverter(
            Long number, LongFunction<T> converter) {
        String numberStr = String.valueOf(number);

        // Compute the overall sum of digits.
        int sum = numberStr.chars()
                .map(c -> c - '0')
                .sum();

        // Build the frequency map using streams and group by digit.
        Map<Integer, T> frequencyMap = numberStr.chars()
                .map(c -> c - '0')             // Convert each character to its digit.
                .boxed()                       // Box the int primitives into Integer.
                .collect(Collectors.toMap(
                        // Key mapper: the digit itself.
                        Function.identity(),
                        // Value mapper: initialize each occurrence as the converted identity (i.e., converter.apply(1L)).
                        digit -> converter.apply(1L),
                        // Merge function: when the same digit appears more than once, add the counts using the converter.
                        (existing, additional) -> converter.apply(existing.longValue() + additional.longValue())
                ));

        return new DigitStatisticsRecord<>(sum, frequencyMap);
    }


    /**
     * Computes the overall sum of digits in the given number and creates a frequency map
     * where each key is a digit (0-9) and the value is its frequency count, of a generic numeric type.
     *
     * <p>This method uses Java Streams to process the string representation of the number,
     * mapping each character to its numeric digit, and then collecting them into a map. The map
     * is constructed by grouping by digit and using a merge function to add the counts.
     *
     * @param number   the input number to process.
     * @param identity the initial count for each occurrence (e.g. 1, 1L, 1f, 1d).
     * @param adder    a BinaryOperator to sum two counts (e.g. Integer::sum, Long::sum, etc.).
     * @param <T>      the numeric type for frequency counts.
     * @return a frequency map where keys are digits and values are counts of type T.
     */
    public static <T extends Number> Map<Integer, T> computeFrequencyMap(Long number, T identity, BinaryOperator<T> adder) {
        String numberStr = String.valueOf(number);

        return numberStr.chars()
                .map(c -> c - '0') // Convert char code to its digit.
                .boxed()           // Convert IntStream to Stream<Integer>.
                .collect(Collectors.toMap(
                        Function.identity(),    // Key: the digit itself.
                        digit -> identity,      // Initial value: identity.
                        adder // Merge function: add counts.
                ));
    }

    /**
     * Computes the overall sum of digits and a frequency map from the given number.
     * The frequency counts are stored as type T, using the provided identity value and adder function.
     *
     * <p>This method performs the following steps:
     * <ol>
     *   <li>Converts the number to its string representation.</li>
     *   <li>Computes the sum of digits by mapping each character to its numeric value.</li>
     *   <li>Iterates over the characters and, for each digit, updates the frequency map by
     *       adding the identity value using the provided adder function.</li>
     * </ol>
     *
     * @param number   the input number.
     * @param identity the initial count for a digit (e.g., 1, 1L, 1f, or 1d).
     * @param adder    a BinaryOperator to combine two count values (e.g., Integer::sum, Long::sum, etc.).
     * @param <T>      the numeric type for frequency counts.
     * @return a {@code DigitStatisticsRecord<T>} containing the overall sum of digits and a frequency map.
     */
    public static <T extends Number> DigitStatisticsRecord<T> computeDigitsIterator(
            Long number, T identity, BinaryOperator<T> adder) {

        String numberStr = String.valueOf(number);
        // Compute the sum of digits.
        int sum = numberStr.chars().map(c -> c - '0').sum();

        // Build the frequency map directly using the provided generic type T.
        Map<Integer, T> frequencyMap = new HashMap<>();
        for (char c : numberStr.toCharArray()) {
            int digit = c - '0';
            // Use compute() to update the count atomically:
            frequencyMap.compute(digit, (key, value) ->
                    (value == null) ? identity : adder.apply(value, identity));
        }

        return new DigitStatisticsRecord<>(sum, frequencyMap);
    }

}
