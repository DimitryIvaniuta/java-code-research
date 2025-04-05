package com.code.research.algorithm;

public class SpiralMatrixII {

    /**
     * Generates an n x n matrix filled with elements from 1 to n^2 in spiral order.
     *
     * @param n the size of the matrix (n x n).
     * @return a 2D integer array representing the spiral matrix.
     */
    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];

        // Boundaries for the spiral traversal
        int left = 0, right = n - 1;
        int top = 0, bottom = n - 1;

        // The current number to place, from 1 to n^2
        int num = 1;
        int target = n * n;

        // Fill the matrix in layers
        while (num <= target) {

            // 1. Fill top row from left to right
            for (int col = left; col <= right && num <= target; col++) {
                matrix[top][col] = num++;
            }
            top++;

            // 2. Fill right column from top to bottom
            for (int row = top; row <= bottom && num <= target; row++) {
                matrix[row][right] = num++;
            }
            right--;

            // 3. Fill bottom row from right to left
            for (int col = right; col >= left && num <= target; col--) {
                matrix[bottom][col] = num++;
            }
            bottom--;

            // 4. Fill left column from bottom to top
            for (int row = bottom; row >= top && num <= target; row--) {
                matrix[row][left] = num++;
            }
            left++;
        }

        return matrix;
    }
}