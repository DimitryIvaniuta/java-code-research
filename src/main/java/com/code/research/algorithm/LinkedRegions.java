package com.code.research.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.*;

@Slf4j
public class LinkedRegions {
    static final int[][] DIRS = {{1,0},{-1,0},{0,1},{0,-1}}; // vertical & horizontal
    static final class Pos {
        final int r, c;
        Pos(int r, int c) { this.r = r; this.c = c; }
        @Override public String toString() { return "(" + r + "," + c + ")"; }
    }

    /** Finds regions of "1" (4-directional) and groups cells by a fresh UUID per region. */
    public static Map<UUID, List<Pos>> findRegions(String[][] grid) {
        if (grid == null || grid.length == 0) return Collections.emptyMap();
        int rows = grid.length, cols = grid[0].length;
        boolean[][] seen = new boolean[rows][cols];
        Map<UUID, List<Pos>> regions = new LinkedHashMap<>(); // preserve discovery order
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!seen[r][c] && "1".equals(grid[r][c])) {
                    UUID id = UUID.randomUUID();
                    List<Pos> cells = new ArrayList<>();
                    dfs(grid, r, c, seen, cells);
                    regions.put(id, cells);
                }
            }
        }
        return regions;
    }

    private static void dfs(String[][] g, int r, int c, boolean[][] seen, List<Pos> acc) {
        seen[r][c] = true;
        acc.add(new Pos(r, c));
        for (int[] d : DIRS) {
            int nr = r + d[0], nc = c + d[1];
            if (inBounds(g, nr, nc) && !seen[nr][nc] && "1".equals(g[nr][nc])) {
                dfs(g, nr, nc, seen, acc);
            }
        }
    }
    private static boolean inBounds(String[][] g, int r, int c) {
        return r >= 0 && r < g.length && c >= 0 && c < g[0].length;
    }
    public static void main(String[] args) {
        String[][] grid = {
                {"1", "0", "1", "1", "0"},
                {"0", "1", "0", "0", "0"},
                {"1", "1", "0", "1", "0"},
                {"0", "0", "0", "1", "1"}
        };
        Map<UUID, List<Pos>> regions = findRegions(grid);
        log.info("Regions found: {}", regions.size()); // expected: 4
        regions.forEach((id, cells) -> System.out.println(id + " -> " + cells));
    }
}
