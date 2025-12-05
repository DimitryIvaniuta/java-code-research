package com.code.research.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MergeIntervalsWithCollections {

    public static int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new int[0][2]; // empty input → empty output
        }

        // 1) Sort intervals by start using Streams
        List<int[]> sorted = Arrays.stream(intervals)
                .sorted(Comparator.comparingInt(a -> a[0]))
                .toList();

        // 2) Merge using a mutable List
        List<int[]> merged = new ArrayList<>();
        int[] current = Arrays.copyOf(sorted.getFirst(), 2); // start with first interval

        for (int i = 1; i < sorted.size(); i++) {
            int[] next = sorted.get(i);

            // Overlap OR touching: [a,b] and [c,d] with c <= b
            if (next[0] <= current[1]) {
                current[1] = Math.max(current[1], next[1]); // extend end
            } else {
                merged.add(current);                        // push finished interval
                current = Arrays.copyOf(next, 2);          // start new current
            }
        }

        // add the last interval
        merged.add(current);

        // 3) Convert List<int[]> → int[][]
        return merged.toArray(new int[merged.size()][]);
    }

    // quick sanity test
    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(
                merge(new int[][]{{8,10},{1,3},{2,6},{15,18}})
        )); // [[1, 6], [8, 10], [15, 18]]

        System.out.println(Arrays.deepToString(
                merge(new int[][]{{1,4},{4,5}})
        )); // [[1, 5]]

        System.out.println(Arrays.deepToString(
                merge(new int[][]{{1,4},{2,3}})
        )); // [[1, 4]]

        System.out.println(Arrays.deepToString(
                merge(new int[][]{{1,3}})
        )); // [[1, 3]]

        System.out.println(Arrays.deepToString(
                merge(new int[][]{}))
        ); // []

        System.out.println(Arrays.deepToString(
                merge(new int[][]{{1,4},{0,2},{3,5}})
        )); // [[0, 5]]
    }

}
