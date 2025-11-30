package com.code.research.algorithm.streams;

import java.util.List;
import java.util.LongSummaryStatistics;

public class StatsUtils {
    /**
     * Computes min, max, sum, count (and average) of the given list of Longs
     * using LongSummaryStatistics.
     *
     * @param numbers the input List<Long>
     * @return a LongSummaryStatistics containing min, max, sum, count, average
     */
    public static LongSummaryStatistics computeStatistics(List<Long> numbers) {
        return numbers.stream().mapToLong(Long::longValue).summaryStatistics();
    }
    // Example usage
    public static void main(String[] args) {
        List<Long> data = List.of(5L, 10L, 3L, 42L, 17L);

        LongSummaryStatistics stats = computeStatistics(data);

        System.out.printf("Count: %d%n", stats.getCount());
        System.out.printf("Sum:   %d%n", stats.getSum());
        System.out.printf("Min:   %d%n", stats.getMin());
        System.out.printf("Max:   %d%n", stats.getMax());
        System.out.printf("Avg:   %.2f%n", stats.getAverage());
    }
}
