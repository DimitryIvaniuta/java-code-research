package com.code.research.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class MergeOverlappingIntervals2 {

    public static int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new int[0][];
        }

        // 1) Sort by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> result = new ArrayList<>();
        int[] current = intervals[0].clone(); // [start, end] of current merged interval

        for (int i = 1; i < intervals.length; i++) {
            int[] next = intervals[i];

            // Overlap or touching: merge
            if (next[0] <= current[1]) {
                current[1] = Math.max(current[1], next[1]);
            } else {
                // No overlap: push current and move on
                result.add(current);
                current = next.clone();
            }
        }

        // Add last interval
        result.add(current);

        return result.toArray(new int[result.size()][]);
    }

    // Optional quick sanity test
    public static void main(String[] args) {

        System.out.println(Arrays.deepToString(
                merge(new int[][]{{8, 10}, {1, 3}, {2, 6}, {15, 18}})
        )); // [[1, 6], [8, 10], [15, 18]]

        System.out.println(Arrays.deepToString(
                merge(new int[][]{{1, 4}, {4, 5}})
        )); // [[1, 5]]

        System.out.println(Arrays.deepToString(
                merge(new int[][]{{1, 4}, {2, 3}})
        )); // [[1, 4]]

        System.out.println(Arrays.deepToString(
                merge(new int[][]{{1, 3}})
        )); // [[1, 3]]

        System.out.println(Arrays.deepToString(
                merge(new int[][]{}))
        ); // []

        System.out.println(Arrays.deepToString(
                merge(new int[][]{{1, 4}, {0, 2}, {3, 5}})
        )); // [[0, 5]]
    }

}
