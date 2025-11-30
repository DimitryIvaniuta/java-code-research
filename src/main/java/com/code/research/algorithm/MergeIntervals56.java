package com.code.research.algorithm;

import java.util.*;

/**
 * 56. Merge Intervals — simple, obvious solution
 */
public final class MergeIntervals56 {
    // Merge all overlapping intervals; intervals[i] = [start, end]
    public static int[][] merge(int[][] intervals) {
        // nothing to merge
        if (intervals.length <= 1) {
            return intervals;
        }
        // sort by start
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        // merged result
        List<int[]> out = new ArrayList<>();
        // current merged interval [s,e]
        int[] cur = intervals[0].clone();
        for (int i = 1; i < intervals.length; i++) {
            int[] iv = intervals[i];
            // overlap or touching (iv.start <= cur.end)
            if (iv[0] <= cur[1]) {
                // extend end
                cur[1] = Math.max(cur[1], iv[1]);
            } else {
                // push finished block
                out.add(cur);
                // start new block
                cur = iv.clone();
            }
        }
        // add the last block
        out.add(cur);
        // list -> array
        return out.toArray(new int[out.size()][]);
    }

    // tiny demo
    public static void main(String[] args) {
        // [[1, 6], [8, 10], [15, 18]]
        System.out.println(Arrays.deepToString(
                merge(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}})));
        // [[1, 5]]
        System.out.println(Arrays.deepToString(
                merge(new int[][]{{1, 4}, {4, 5}})));
        // [[1, 7]]
        System.out.println(Arrays.deepToString(
                merge(new int[][]{{4, 7}, {1, 4}})));
    }
}
