package com.code.research.datastructures.heaps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MedianFinderApplication {

    /**
     * Main method demonstrating the usage of MedianFinder.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        MedianFinder medianFinder = new MedianFinder();

        // Add numbers to the MedianFinder and display the median.
        medianFinder.addNum(1);
        log.info("Median: {}", medianFinder.findMedian()); // Output: 1.0

        medianFinder.addNum(2);
        log.info("Median: {}", medianFinder.findMedian()); // Output: 1.5

        medianFinder.addNum(3);
        log.info("Median: {}", medianFinder.findMedian()); // Output: 2.0

        medianFinder.addNum(4);
        log.info("Median: {}", medianFinder.findMedian()); // Output: 2.5

        medianFinder.addNum(5);
        log.info("Median: {}", medianFinder.findMedian()); // Output: 3.0
    }
    
}
