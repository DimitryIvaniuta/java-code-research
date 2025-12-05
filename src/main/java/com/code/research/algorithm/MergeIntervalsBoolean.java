package com.code.research.algorithm;

import java.util.Arrays;

public class MergeIntervalsBoolean {

    public static int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new int[0][2];
        }

        // 1) Find global min and max
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int[] iv : intervals) {
            if (iv[0] < 0 || iv[1] < 0) {
                throw new IllegalArgumentException("Only non-negative endpoints supported in this approach");
            }
            if (iv[0] > iv[1]) {
                throw new IllegalArgumentException("Invalid interval: start > end");
            }
            if (iv[0] < min) min = iv[0];
            if (iv[1] > max) max = iv[1];
        }

        int range = max - min + 1; // inclusive range
        boolean[] used = new boolean[range];

        // 2) Mark all covered points as true
        for (int[] iv : intervals) {
            int start = iv[0] - min;
            int end = iv[1] - min;
            for (int i = start; i <= end; i++) {
                used[i] = true;
            }
        }

        // 3) First scan: count merged segments
        int count = 0;
        boolean inside = false;

        for (int i = 0; i < range; i++) {
            if (used[i]) {
                if (!inside) {
                    inside = true;   // starting new segment
                }
            } else {
                if (inside) {
                    inside = false;  // closing segment
                    count++;
                }
            }
        }
        if (inside) {
            count++;
        }

        // 4) Second scan: build result intervals
        int[][] result = new int[count][2];
        int idx = 0;
        inside = false;
        int startIndex = -1;

        for (int i = 0; i < range; i++) {
            if (used[i]) {
                if (!inside) {
                    inside = true;
                    startIndex = i;
                }
            } else {
                if (inside) {
                    inside = false;
                    result[idx][0] = startIndex + min;
                    result[idx][1] = (i - 1) + min;
                    idx++;
                }
            }
        }
        if (inside) {
            result[idx][0] = startIndex + min;
            result[idx][1] = (range - 1) + min;
        }

        return result;
    }

    // sanity test
    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(
                merge(new int[][]{{8, 10}, {1, 3}, {2, 6}, {15, 18}})
        )); // [[1, 6], [8, 10], [15, 18]]

        System.out.println(Arrays.deepToString(
                merge(new int[][]{{1, 4}, {4, 5}})
        )); // [[1, 5]]

        System.out.println(Arrays.deepToString(
                merge(new int[][]{{1, 4}, {0, 2}, {3, 5}})
        )); // [[0, 5]]
    }

}
