package com.code.research.algorithm;

public final class LongestDistanceRecursiveApp {

    // Public API: longest strictly-increasing path length starting from (x,y)
    public static int longestDistance(int[][] matrix, int x, int y) {
        int m = matrix.length, n = matrix[0].length;
        // memo[r][c] = best length from (r,c); 0 = unknown
        int[][] memo = new int[m][n];
        return dfs(matrix, x, y, memo);
    }

    // Pure recursion + memoization (top-down DP)
    private static int dfs(int[][] a, int r, int c, int[][] memo) {
        // cached result
        if (memo[r][c] != 0) {
            return memo[r][c];
        }
        // at least the cell itself
        int best = 1;
        // explore 4 directions
        if (r + 1 < a.length && a[r + 1][c] > a[r][c]) {
            best = Math.max(best, 1 + dfs(a, r + 1, c, memo));
        }
        if (r - 1 >= 0 && a[r - 1][c] > a[r][c]) {
            best = Math.max(best, 1 + dfs(a, r - 1, c, memo));
        }
        if (c + 1 < a[0].length && a[r][c + 1] > a[r][c]) {
            best = Math.max(best, 1 + dfs(a, r, c + 1, memo));
        }
        if (c - 1 >= 0 && a[r][c - 1] > a[r][c]) {
            best = Math.max(best, 1 + dfs(a, r, c - 1, memo));
        }
        // store & return
        return memo[r][c] = best;
    }

    // tiny demo
    public static void main(String[] args) {
        int[][] matrix = {
                {9, 9, 4},
                {6, 6, 8},
                {2, 1, 1}
        };
        // Path length in cells (moves = length - 1)
        // start at 1 -> 1→2→6→9 => 4
        System.out.println(longestDistance(matrix, 2, 2));
        // start at 9 -> 1
        System.out.println(longestDistance(matrix, 0, 0));
        // start at 8 -> 8→9 => 2
        System.out.println(longestDistance(matrix, 1, 2));
    }
}
