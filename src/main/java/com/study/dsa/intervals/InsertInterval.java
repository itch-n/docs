package com.study.dsa.intervals;

import java.util.*;

public class InsertInterval {

    /**
     * Problem: Insert interval into sorted non-overlapping list
     * Time: O(n), Space: O(n)
     *
     * TODO: Implement three-phase scan
     * Phase 1: Add all intervals that END before newInterval starts
     *          (no overlap: intervals[i][1] < newInterval[0])
     * Phase 2: Merge all intervals that overlap with newInterval
     *          (overlap condition: intervals[i][0] <= newInterval[1])
     *          Expand newInterval: start = min(both starts), end = max(both ends)
     * Phase 3: Add all remaining intervals
     */
    public static int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0, n = intervals.length;

        // TODO: Phase 1 — add all intervals ending before newInterval starts

        // TODO: Phase 2 — merge all overlapping intervals into newInterval

        // TODO: Add merged newInterval

        // TODO: Phase 3 — add all remaining intervals

        return result.toArray(new int[0][]);
    }


    // --- demo (moved from InsertIntervalClient) ---

public static void main(String[] args) {
        System.out.println("=== Insert Interval ===\n");

        Object[][] tests = {
            new Object[]{new int[][]{{1,3},{6,9}}, new int[]{2,5}},
            new Object[]{new int[][]{{1,2},{3,5},{6,7},{8,10},{12,16}}, new int[]{4,8}},
            new Object[]{new int[][]{}, new int[]{5,7}},
            new Object[]{new int[][]{{1,5}}, new int[]{2,3}},    // New inside existing
        };

        for (Object[] test : tests) {
            int[][] intervals = (int[][]) test[0];
            int[] newInterval = (int[]) test[1];
            System.out.println("Intervals:   " + Arrays.deepToString(intervals));
            System.out.println("New:         " + Arrays.toString(newInterval));
            int[][] result = insert(intervals, newInterval);
            System.out.println("Result:      " + Arrays.deepToString(result));
            System.out.println();
        }
    }
}
