package com.code.research.datastructures.algorithm.backtracking;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class NQueensSolverApplication {

    /**
     * Main method demonstrating the use of the NQueensSolver.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        int n = 4; // Example: 4-Queens problem
        List<List<String>> solutions = NQueensSolver.solveNQueens(n);
        log.info("Total solutions for {}-Queens: {}", n, solutions.size());
        for (List<String> solution : solutions) {
            for (String row : solution) {
                log.info("{} ", row);
            }
            log.info("\n");
        }
    }
    
}
