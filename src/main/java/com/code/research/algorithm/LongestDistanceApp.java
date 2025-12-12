package com.code.research.algorithm;

public final class LongestDistanceApp {
    // Return length of the longest strictly-increasing path starting at (x,y)
    public static int longestDistance(int[][] a, int x, int y) {
        int m = a.length, n = a[0].length;
        // 0 = unknown; store best length from cell
        int[][] memo = new int[m][n];
        // 4-neighbors
        int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};

        // DFS with memoization (recursion)
        java.util.function.BiFunction<Integer, Integer, Integer> dfs = new java.util.function.BiFunction<>() {
            @Override
            public Integer apply(Integer r, Integer c) {
                // cached
                if (memo[r][c] != 0) {
                    return memo[r][c];
                }
                // path length at least the cell itself
                int best = 1;
                for (int k = 0; k < 4; k++) {
                    int nr = r + dr[k], nc = c + dc[k];
                    if (0 <= nr && nr < m && 0 <= nc && nc < n && a[nr][nc] > a[r][c]) {
                        best = Math.max(best, 1 + apply(nr, nc));
                    }
                }
                // store and return
                return memo[r][c] = best;
            }
        };
        // answer from the start cell
        return dfs.apply(x, y);
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {9, 9, 4},
                {6, 6, 8},
                {2, 1, 1}
        };
        // Examples: result is number of cells on the path (steps = result - 1)
        // start at value 1 -> 1->2->6->9 => 4
        System.out.println(longestDistance(matrix, 2, 2));
        // start at 9 -> no higher neighbor => 1
        System.out.println(longestDistance(matrix, 0, 0));
        // start at 8 -> 8->9 => 2
        System.out.println(longestDistance(matrix, 1, 2));
    }
}
