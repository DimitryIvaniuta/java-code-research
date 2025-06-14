package com.code.research.concurrent;

import java.util.concurrent.RecursiveTask;

/**
 * RecursiveTask that computes the sum of a segment of an integer array using
 * the Fork/Join framework. Splits work when the segment length exceeds a
 * configurable threshold.
 */
public class ForkJoinSumTask extends RecursiveTask<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * When the number of elements is at or below this, compute directly.
     */
    private static final int THRESHOLD = 10_000;

    private final int[] array;
    private final int startInclusive;
    private final int endExclusive;

    /**
     * Constructs a task for the entire array.
     * @param array the array to sum
     */
    public ForkJoinSumTask(int[] array) {
        this(array, 0, array.length);
    }

    /**
     * Constructs a task for the entire array.
     * @param array the array to sum
     */
    public ForkJoinSumTask(final int[] array, final int startInclusive, final int endExclusive) {
        this.array = array;
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
    }

    @Override
    protected Long compute() {
        int length = endExclusive - startInclusive;
        if (length <= THRESHOLD) {
            // Base case: sum directly
            long sum = 0;
            for (int i = startInclusive; i < endExclusive; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            // Split range in two and fork/join
            int mid = startInclusive + length / 2;
            ForkJoinSumTask leftTask = new ForkJoinSumTask(array, startInclusive, mid);
            ForkJoinSumTask rightTask = new ForkJoinSumTask(array, mid, endExclusive);

            // Asynchronously execute the left half
            leftTask.fork();
            // Compute the right half in this thread (depth-first)
            long rightResult = rightTask.compute();
            // Wait for left half and combine results
            long leftResult  = leftTask.join();
            return leftResult + rightResult;
        }
    }

}
