package com.study.dsa.prefixsums;

import java.util.Arrays;

public class NumMatrix {

    private int[][] prefixSum;

    /**
     * Problem: Range sum query 2D
     * Time: O(1) query after O(m*n) preprocessing, Space: O(m*n)
     *
     * TODO: Implement 2D prefix sum
     * prefixSum[i][j] = sum of submatrix from (0,0) to (i-1,j-1)
     * Build using: prefixSum[i][j] = matrix[i-1][j-1]
     *                              + prefixSum[i-1][j]
     *                              + prefixSum[i][j-1]
     *                              - prefixSum[i-1][j-1]
     */
    public NumMatrix(int[][] matrix) {
        // TODO: Build 2D prefix sum with (m+1) x (n+1) dimensions
    }

    /**
     * TODO: Implement sumRegion using inclusion-exclusion:
     * sum = prefixSum[row2+1][col2+1]
     *     - prefixSum[row1][col2+1]
     *     - prefixSum[row2+1][col1]
     *     + prefixSum[row1][col1]
     */
    public int sumRegion(int row1, int col1, int row2, int col2) {
        return 0; // Replace with implementation
    }


    // --- demo (moved from NumMatrixClient) ---

public static void main(String[] args) {
        System.out.println("=== 2D Range Sum Query ===\n");

        int[][] matrix = {
            {3, 0, 1, 4, 2},
            {5, 6, 3, 2, 1},
            {1, 2, 0, 1, 5},
            {4, 1, 0, 1, 7},
            {1, 0, 3, 0, 5}
        };

        NumMatrix nm = new NumMatrix(matrix);

        System.out.println("Matrix:");
        for (int[] row : matrix) {
            System.out.println("  " + Arrays.toString(row));
        }

        System.out.println();
        System.out.printf("sumRegion(2,1,4,3) = %d (expected 8)%n", nm.sumRegion(2,1,4,3));
        System.out.printf("sumRegion(1,1,2,2) = %d (expected 11)%n", nm.sumRegion(1,1,2,2));
        System.out.printf("sumRegion(1,2,2,4) = %d (expected 12)%n", nm.sumRegion(1,2,2,4));
    }
}
