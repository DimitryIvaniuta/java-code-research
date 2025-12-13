package com.code.research.algorithm;

import java.util.*;

public final class LongestIncreasingPath329App {

    // Return length of the longest strictly-increasing path anywhere in the matrix
    public static int longestIncreasingPath(int[][] a) {
        int m = a.length, n = a[0].length, best = 0;
        // memo[r][c] = best length starting at (r,c)
        int[][] memo = new int[m][n];                       
        for (int r = 0; r < m; r++){
            for (int c = 0; c < n; c++) {
                // try each cell as a start
                best = Math.max(best, dfs(a, r, c, memo));
            }
        }         
        return best;
    }

    // DFS + memoization (top-down). From (r,c), move to strictly larger neighbors.
    private static int dfs(int[][] a, int r, int c, int[][] memo) {
        // cached
        if (memo[r][c] != 0) return memo[r][c];
        // at least the cell itself
        int m = a.length, n = a[0].length, best = 1;       
        // up
        if (r > 0 && a[r - 1][c] > a[r][c]) best = Math.max(best, 1 + dfs(a, r - 1, c, memo));
        // down
        if (r + 1 < m && a[r + 1][c] > a[r][c]) best = Math.max(best, 1 + dfs(a, r + 1, c, memo));
        // left
        if (c > 0 && a[r][c - 1] > a[r][c]) best = Math.max(best, 1 + dfs(a, r, c - 1, memo));
        // right
        if (c + 1 < n && a[r][c + 1] > a[r][c]) best = Math.max(best, 1 + dfs(a, r, c + 1, memo));
        return memo[r][c] = best;
    }

    // tiny demo
    public static void main(String[] args) {
        int[][] m1 = {{9, 9, 4}, {6, 6, 8}, {2, 1, 1}};
        int[][] m2 = {{3, 4, 5}, {3, 2, 6}, {2, 2, 1}};
        int[][] m3 = {{1}};
        System.out.println(longestIncreasingPath(m1)); // 4  (1->2->6->9)
        System.out.println(longestIncreasingPath(m2)); // 4  (3->4->5->6)
        System.out.println(longestIncreasingPath(m3)); // 1
    }
}
