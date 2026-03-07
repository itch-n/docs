package com.study.dsa.intervals;

import java.util.*;

public class Intervals {

    /**
     * Problem: Merge overlapping intervals
     * Time: O(n log n), Space: O(n)
     *
     * TODO: Implement merge intervals
     * Step 1: Sort by start time
     * Step 2: Walk the list; if next.start <= current.end, merge
     *         using current.end = Math.max(current.end, next.end)
     * Step 3: Otherwise, push current to result and move on
     * Step 4: Don't forget to add the final current to result
     */
    public static int[][] merge(int[][] intervals) {
        // TODO: Sort by start time
        // TODO: Iterate and merge overlapping intervals

        return new int[0][0]; // Replace with implementation
    }


    // --- demo (moved from MergeClient) ---

    public static void main(String[] args) {
        System.out.println("=== Merge Intervals ===\n");

        int[][][] tests = {
            {{1,3}, {2,6}, {8,10}, {15,18}},
            {{1,4}, {4,5}},
            {{1,4}, {2,3}},    // Fully contained
            {{1,4}, {0,4}},    // Starts before
        };

        for (int[][] test : tests) {
            System.out.println("Input:  " + Arrays.deepToString(test));
            int[][] result = merge(test);
            System.out.println("Output: " + Arrays.deepToString(result));
            System.out.println();
        }
    }
}
