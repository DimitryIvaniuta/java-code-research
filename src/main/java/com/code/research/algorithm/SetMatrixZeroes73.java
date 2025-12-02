package com.code.research.algorithm;

import java.util.Arrays;

public final class SetMatrixZeroes73 {
    // In-place, O(1) extra space using first row/col as markers
    public static void setZeroes(int[][] a) {
        int m = a.length;
        int n = a[0].length;

        // Will we need to zero the first row/col themselves?
        boolean firstRowZero = false;
        boolean firstColZero = false;
        for (int j = 0; j < n; j++){
            if (a[0][j] == 0) {
                firstRowZero = true;
            }
        }
        for (int i = 0; i < m; i++) {
            if (a[i][0] == 0) {
                firstColZero = true;
            }
        }

        // Mark rows/cols to zero using cell (i,0) and (0,j)
        for (int i = 1; i < m; i++){
            for (int j = 1; j < n; j++){
                if (a[i][j] == 0) {
                    a[i][0] = 0;
                    a[0][j] = 0;
                }
            }
        }

        // Zero cells based on markers
        for (int i = 1; i < m; i++){
            for (int j = 1; j < n; j++) {
                if (a[i][0] == 0 || a[0][j] == 0) {
                    a[i][j] = 0;
                }
            }
        }

        // Zero first row/col if needed
        if (firstRowZero) {
            for (int j = 0; j < n; j++) {
                a[0][j] = 0;
            }
        }
        if (firstColZero) {
            for (int i = 0; i < m; i++) {
                a[i][0] = 0;
            }
        }
    }

    // tiny demo
    public static void main(String[] args) {
        int[][] m1 = {{1,1,1},{1,0,1},{1,1,1}};
        setZeroes(m1);
        // [[1,0,1],[0,0,0],[1,0,1]]
        System.out.println(Arrays.deepToString(m1));

        int[][] m2 = {{0,1,2,0},{3,4,5,2},{1,3,1,5}};
        setZeroes(m2);
        // [[0,0,0,0],[0,4,5,0],[0,3,1,0]]
        System.out.println(Arrays.deepToString(m2));
    }
}
