package com.code.research.algorithm;

public final class WordSearch {
    // Return true if `word` can be formed by adjacent (4-dir) cells without reusing a cell
    public static boolean exist(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        boolean[][] used = new boolean[m][n];                 // marks cells taken in current path
        for (int r = 0; r < m; r++)                           // try each cell as a start
            for (int c = 0; c < n; c++)
                if (dfs(board, word, 0, r, c, used)) return true;
        return false;
    }

    // Depth-first search from (r,c) to match word[idx..]
    private static boolean dfs(char[][] b, String w, int idx, int r, int c, boolean[][] used) {
        if (idx == w.length()) return true;                   // matched all chars
        if (r < 0 || c < 0 || r >= b.length || c >= b[0].length) return false; // out of bounds
        if (used[r][c] || b[r][c] != w.charAt(idx)) return false; // taken or char mismatch

        used[r][c] = true;                                    // choose this cell
        // explore 4 directions
        boolean ok = dfs(b, w, idx + 1, r + 1, c, used) ||
                dfs(b, w, idx + 1, r - 1, c, used) ||
                dfs(b, w, idx + 1, r, c + 1, used) ||
                dfs(b, w, idx + 1, r, c - 1, used);
        used[r][c] = false;                                   // backtrack
        return ok;
    }

    // tiny demo
    public static void main(String[] args) {
        char[][] grid = {
                {'A','B','C','E'},
                {'S','F','C','S'},
                {'A','D','E','E'}
        };
        System.out.println(exist(grid, "ABCCED")); // true
        System.out.println(exist(grid, "SEE"));    // true
        System.out.println(exist(grid, "ABCB"));   // false
    }
}
