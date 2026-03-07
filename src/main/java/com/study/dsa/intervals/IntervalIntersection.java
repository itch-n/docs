package com.study.dsa.intervals;

import java.util.*;

public class IntervalIntersection {

    /**
     * Problem: Interval list intersection
     * Time: O(m + n), Space: O(min(m,n))
     *
     * TODO: Implement two-pointer intersection
     * An intersection exists when max(start1,start2) <= min(end1,end2)
     * Advance the pointer whose interval ends first
     */
    public static int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<int[]> result = new ArrayList<>();
        int i = 0, j = 0;

        // TODO: Advance both pointers, check for intersection each step
        // TODO: Move pointer of interval that ends first

        return result.toArray(new int[0][]);
    }


    // --- demo (moved from IntersectionClient) ---

    public static void main(String[] args) {
        System.out.println("=== Interval Intersection ===\n");

        int[][][] firstLists = {
            {{0,2},{5,10},{13,23},{24,25}},
            {{1,3},{5,9}},
        };
        int[][][] secondLists = {
            {{1,5},{8,12},{15,24},{25,26}},
            {},
        };

        for (int t = 0; t < firstLists.length; t++) {
            System.out.println("First:  " + Arrays.deepToString(firstLists[t]));
            System.out.println("Second: " + Arrays.deepToString(secondLists[t]));
            int[][] result = intervalIntersection(firstLists[t], secondLists[t]);
            System.out.println("Result: " + Arrays.deepToString(result));
            System.out.println();
        }
    }
}
