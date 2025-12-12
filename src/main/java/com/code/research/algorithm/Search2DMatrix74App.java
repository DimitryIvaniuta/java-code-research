package com.code.research.algorithm;

import java.util.Arrays;

public final class Search2DMatrix74App {

    // O(log(m*n)) by treating the matrix as a flat sorted array
    public static boolean searchMatrix(int[][] mat, int target) {
        int m = mat.length, n = mat[0].length;
        // search over virtual 1D range
        int l = 0;
        int r = m * n - 1;
        while (l <= r) {
            int mid = l + ((r - l) >>> 1);
            // map 1D index -> 2D cell
            int row = mid / n, col = mid % n;
            int val = mat[row][col];
            // found
            if (val == target) {
                return true;
            }
            if (val < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        // not found
        return false;
    }

    public static void main(String[] args) {
        int[][] m1 = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        System.out.println(Arrays.deepToString(m1) + " target=3  -> " + searchMatrix(m1, 3));   // true
        System.out.println(Arrays.deepToString(m1) + " target=13 -> " + searchMatrix(m1, 13));  // false

        int[][] m2 = {{1}, {3}, {5}};
        System.out.println(Arrays.deepToString(m2) + " target=5  -> " + searchMatrix(m2, 5));   // true
    }
}
