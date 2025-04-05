package com.code.research.algorithm;

public class SpiralMatrixIIApp {

    // A main method to demonstrate usage
    public static void main(String[] args) {
        SpiralMatrixII spiral = new SpiralMatrixII();

        int n1 = 5;
        int[][] result1 = spiral.generateMatrix(n1);
        System.out.println("Spiral matrix for n = " + n1 + ":");
        printMatrix(result1);

        int n2 = 1;
        int[][] result2 = spiral.generateMatrix(n2);
        System.out.println("Spiral matrix for n = " + n2 + ":");
        printMatrix(result2);
    }

    // Utility method to print a 2D matrix
    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
