package com.code.research.datastructures.heaps;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * MedianFinder is a data structure that supports dynamic retrieval of the median
 * from a stream of numbers. It uses two heaps:
 * - A max-heap for the lower half of numbers.
 * - A min-heap for the upper half of numbers.
 * This approach is used in real-time analytics, online statistics, and streaming data processing.
 */
public class MedianFinder {

    /**
     * A max-heap that stores the lower half of the numbers.
     * The largest element of the lower half is at the root.
     */
    private final PriorityQueue<Integer> maxHeap;

    /**
     * A min-heap that stores the upper half of the numbers.
     * The smallest element of the upper half is at the root.
     */
    private final PriorityQueue<Integer> minHeap;

    /**
     * Constructs a new MedianFinder.
     */
    public MedianFinder() {
        // Initialize maxHeap as a max-heap using reverse order.
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        // Initialize minHeap as a normal (min) heap.
        minHeap = new PriorityQueue<>();
    }

    /**
     * Adds a new number to the data structure.
     * Balances the two heaps to ensure the median can be efficiently computed.
     *
     * @param num the number to add.
     */
    public void addNum(int num) {
        // Step 1: Add the new number to maxHeap.
        maxHeap.offer(num);

        // Step 2: Move the largest element from maxHeap to minHeap
        // to maintain the order: maxHeap elements <= minHeap elements.
        minHeap.offer(maxHeap.poll());

        // Step 3: Balance the heaps: If minHeap has more elements than maxHeap,
        // move the smallest element from minHeap back to maxHeap.
        if (maxHeap.size() < minHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    /**
     * Returns the median of all elements so far.
     *
     * @return the median as a double.
     * @throws IllegalStateException if no numbers have been added.
     */
    public double findMedian() {
        if (maxHeap.isEmpty()) {
            throw new IllegalStateException("No numbers are available.");
        }
        // If maxHeap has more elements, the median is its root.
        if (maxHeap.size() > minHeap.size()) {
            return maxHeap.peek();
        } else {
            // Otherwise, the median is the average of the two roots.
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }

}
