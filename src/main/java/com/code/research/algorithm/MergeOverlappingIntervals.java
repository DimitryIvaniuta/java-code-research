package com.code.research.algorithm;

import java.util.Arrays;
import java.util.Comparator;

public class MergeOverlappingIntervals {

    public static int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new int[0][2]; // empty input → empty output
        }

        // 1) Sort by start
        System.out.println("Before Sort: " + Arrays.deepToString(intervals));
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        System.out.println("After Sort: " + Arrays.deepToString(intervals));
        // 2) Merge in-place
        int idx = 0; // last merged interval index
        for (int i = 1; i < intervals.length; i++) {
            int[] last = intervals[idx];
            int[] curr = intervals[i];

            // Overlap OR touching: [a,b] and [c,d] with c <= b
            if (curr[0] <= last[1]) {
                System.out.printf(" Cur[0]: %d : Last[1]: %d ::\n", curr[0], last[1]);
                last[1] = Math.max(last[1], curr[1]); // extend end
                System.out.printf("Last[0]: %d | Last[1]: %d |\n", last[0], last[1]);
            } else {
                // No overlap: move forward and copy
                idx++;
                intervals[idx] = curr;
                System.out.printf("Cur[0]: %d | Cur[1]: %d |\n", curr[0], curr[1]);
            }
        }

        // Copy only merged part [0..idx]
        return Arrays.copyOf(intervals, idx + 1);
    }

    // Quick check against all examples from the task
    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(
                merge(new int[][]{{8,10},{1,3},{2,6},{15,18}})
        )); // [[1, 6], [8, 10], [15, 18]]

        System.out.println(Arrays.deepToString(
                merge(new int[][]{{1,4},{4,5}})
        )); // [[1, 5]] (touching intervals merge)

        System.out.println(Arrays.deepToString(
                merge(new int[][]{{1,4},{2,3}})
        )); // [[1, 4]] (one inside another)

        System.out.println(Arrays.deepToString(
                merge(new int[][]{{1,3}})
        )); // [[1, 3]] (single interval)

        System.out.println(Arrays.deepToString(
                merge(new int[][]{})
        )); // [] (empty input)

        System.out.println(Arrays.deepToString(
                merge(new int[][]{{1,4},{0,2},{3,5}})
        )); // [[0, 5]] (all merge into one)
    }
}
