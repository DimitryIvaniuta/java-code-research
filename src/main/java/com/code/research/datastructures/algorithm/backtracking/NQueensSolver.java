package com.code.research.datastructures.algorithm.backtracking;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * NQueensSolver uses recursion and backtracking to solve the N-Queens problem.
 *
 * <p>The goal is to place N queens on an N×N chessboard so that no two queens attack each other.
 * This is achieved by exploring all potential placements row by row, and backtracking when a conflict is detected.
 *
 * <p>Real-world applications of backtracking include scheduling, combinatorial optimization, and puzzle solving.
 *
 * @author Dzmitry Ivaniuta
 */
public class NQueensSolver {

    private NQueensSolver() {
        //
    }

    /**
     * Solves the N-Queens problem and returns all distinct solutions.
     *
     * <p>Each solution is represented as a list of strings, where each string represents a row of the chessboard.
     * A 'Q' denotes a queen and a '.' denotes an empty space.
     *
     * @param n the number of queens and the size of the chessboard.
     * @return a list of solutions, where each solution is a list of strings.
     */
    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> solutions = new ArrayList<>();
        // queens[row] represents the column index of the queen placed in that row.
        int[] queens = new int[n];
        Arrays.fill(queens, -1);

        // Boolean arrays to track which columns and diagonals are occupied.
        boolean[] cols = new boolean[n];                  // columns
        boolean[] diag1 = new boolean[2 * n - 1];           // main diagonal: row - col + (n - 1)
        boolean[] diag2 = new boolean[2 * n - 1];           // anti-diagonal: row + col

        backtrack(solutions, queens, 0, n, cols, diag1, diag2);
        return solutions;
    }

    /**
     * Recursively places queens on the board using backtracking.
     *
     * @param solutions the list to collect valid board configurations.
     * @param queens    an array where queens[i] is the column index for the queen in row i.
     * @param row       the current row to place a queen.
     * @param n         the size of the board.
     * @param cols      boolean array indicating if a column is already occupied.
     * @param diag1     boolean array for main diagonals occupancy.
     * @param diag2     boolean array for anti-diagonals occupancy.
     */
    private static void backtrack(List<List<String>> solutions, int[] queens, int row, int n,
                           boolean[] cols, boolean[] diag1, boolean[] diag2) {
        if (row == n) {
            solutions.add(generateBoard(queens, n));
            return;
        }
        for (int col = 0; col < n; col++) {
            int d1 = row - col + n - 1; // index for main diagonal
            int d2 = row + col;         // index for anti-diagonal
            // If placing a queen here would cause a conflict, skip it.
            if (cols[col] || diag1[d1] || diag2[d2]) {
                continue;
            }
            // Place queen at row 'row', column 'col'
            queens[row] = col;
            cols[col] = true;
            diag1[d1] = true;
            diag2[d2] = true;
            // Recurse to place the next queen.
            backtrack(solutions, queens, row + 1, n, cols, diag1, diag2);
            // Backtrack: remove queen and reset the flags.
            queens[row] = -1;
            cols[col] = false;
            diag1[d1] = false;
            diag2[d2] = false;
        }
    }

    /**
     * Generates a board configuration (list of strings) based on queen placements.
     *
     * @param queens an array where queens[i] is the column position of the queen in row i.
     * @param n      the size of the board.
     * @return a list of strings representing the board.
     */
    private static List<String> generateBoard(int[] queens, int n) {
        List<String> board = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            char[] row = new char[n];
            Arrays.fill(row, '.');
            row[queens[i]] = 'Q';
            board.add(new String(row));
        }
        return board;
    }

}
