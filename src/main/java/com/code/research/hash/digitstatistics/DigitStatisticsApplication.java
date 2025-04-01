package com.code.research.hash.digitstatistics;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class DigitStatisticsApplication {

    public static void main(String[] args) {
        DigitStatisticsLongRecord digitsStatistics = DigitStatistics.computeDigits(33423532582L);
        log.info("Sum: {} : Count: {}", digitsStatistics.sum(), digitsStatistics.frequencyMap());

        String numberStr = "2232423";
        // Convert to IntStream, map to digits, box to Stream<Integer>, then group and count
        Map<Integer, Long> countMap = numberStr.chars()
                .map(c -> c - '0')   // Convert character code to digit
                .boxed()             // Convert IntStream to Stream<Integer>
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        log.info("Digit Frequency Map: {}", countMap);

        int number = 123445322;
        DigitStatisticsRecord<Integer> stats = DigitStatistics.computeDigitStatistics(number);
        log.info("Number: {}", number);
        log.info("Sum of digits: {}", stats.sum());
        log.info("Frequency Map: {}", stats.frequencyMap());
    }
}
