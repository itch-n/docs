package com.study.dsa.hashtables;

import java.util.*;

public class HashSetOperations {

    /**
     * Problem: Find intersection of two arrays
     * Time: O(n + m), Space: O(min(n, m))
     *
     * TODO: Implement using HashSet
     */
    public static int[] intersection(int[] nums1, int[] nums2) {
        // TODO: Store one array in a set for fast lookup
        // How do you find common elements?

        return new int[0]; // Replace with implementation
    }

    /**
     * Problem: Find missing number from 0 to n
     * Time: O(n), Space: O(n)
     *
     * TODO: Implement using HashSet
     */
    public static int missingNumber(int[] nums) {
        // TODO: Store all present numbers
        // How do you check which number is missing?

        return -1; // Replace with implementation
    }

    /**
     * Problem: Longest consecutive sequence
     * Time: O(n), Space: O(n)
     *
     * TODO: Implement using HashSet
     */
    public static int longestConsecutive(int[] nums) {
        // TODO: Store all numbers in a set for O(1) lookup
        // How do you identify the start of a sequence?
        // How do you count consecutive numbers?

        return 0; // Replace with implementation
    }


    // --- demo (moved from HashSetOperationsClient) ---

public static void main(String[] args) {
        System.out.println("=== Hash Set Operations ===\n");

        // Test 1: Intersection
        System.out.println("--- Test 1: Intersection ---");
        int[] arr1 = {1, 2, 2, 1};
        int[] arr2 = {2, 2};

        int[] intersection = intersection(arr1, arr2);
        System.out.printf("Array 1: %s%n", Arrays.toString(arr1));
        System.out.printf("Array 2: %s%n", Arrays.toString(arr2));
        System.out.printf("Intersection: %s%n", Arrays.toString(intersection));

        int[] arr3 = {4, 9, 5};
        int[] arr4 = {9, 4, 9, 8, 4};

        int[] intersection2 = intersection(arr3, arr4);
        System.out.printf("\nArray 1: %s%n", Arrays.toString(arr3));
        System.out.printf("Array 2: %s%n", Arrays.toString(arr4));
        System.out.printf("Intersection: %s%n", Arrays.toString(intersection2));

        // Test 2: Missing number
        System.out.println("\n--- Test 2: Missing Number ---");
        int[][] testArrays = {
            {3, 0, 1},
            {0, 1},
            {9, 6, 4, 2, 3, 5, 7, 0, 1}
        };

        for (int[] arr : testArrays) {
            int missing = missingNumber(arr);
            System.out.printf("Array: %s -> Missing: %d%n",
                Arrays.toString(arr), missing);
        }

        // Test 3: Longest consecutive sequence
        System.out.println("\n--- Test 3: Longest Consecutive ---");
        int[][] sequenceArrays = {
            {100, 4, 200, 1, 3, 2},
            {0, 3, 7, 2, 5, 8, 4, 6, 0, 1},
            {9, 1, -3, 2, 4, 8, 3, -1, 6, -2, -4, 7}
        };

        for (int[] arr : sequenceArrays) {
            int length = longestConsecutive(arr);
            System.out.printf("Array: %s%n", Arrays.toString(arr));
            System.out.printf("Longest consecutive: %d%n%n", length);
        }
    }
}
