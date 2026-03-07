package com.study.dsa.twopointers;

import java.util.Arrays;

public class OppositeDirectionPointers {

    /**
     * Problem: Check if string is a palindrome
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement using two pointers from opposite ends
     */
    public static boolean isPalindrome(String s) {
        // TODO: Use two pointers moving towards each other
        // Consider the loop termination condition

        return false; // Replace with implementation
    }

    /**
     * Problem: Find pair in sorted array that sums to target
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement two-pointer pair sum
     */
    public static int[] twoSum(int[] nums, int target) {
        // TODO: Start pointers at opposite ends
        // How should pointers move based on current sum vs target?

        return new int[] {-1, -1}; // Replace with implementation
    }

    /**
     * Problem: Reverse array in-place
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement using two pointers
     */
    public static void reverseArray(int[] arr) {
        // TODO: Use two pointers to swap elements
        // What positions should they start at?
    }


    // --- demo (moved from OppositeDirectionClient) ---

    public static void main(String[] args) {
        System.out.println("=== Opposite Direction Two Pointers ===\n");

        // Test 1: Palindrome check
        System.out.println("--- Test 1: Palindrome ---");
        String[] testStrings = {"racecar", "hello", "noon", "a", ""};

        for (String s : testStrings) {
            boolean result = isPalindrome(s);
            System.out.printf("isPalindrome(\"%s\") = %b%n", s, result);
        }

        // Test 2: Two sum in sorted array
        System.out.println("\n--- Test 2: Two Sum ---");
        int[] sortedArray = {1, 3, 5, 7, 9, 11};
        int target = 12;

        int[] result = twoSum(sortedArray, target);
        System.out.printf("Array: %s%n", Arrays.toString(sortedArray));
        System.out.printf("Target: %d%n", target);
        System.out.printf("Pair indices: %s%n", Arrays.toString(result));
        if (result[0] != -1) {
            System.out.printf("Values: %d + %d = %d%n",
                sortedArray[result[0]], sortedArray[result[1]], target);
        }

        // Test 3: Reverse array
        System.out.println("\n--- Test 3: Reverse Array ---");
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println("Before: " + Arrays.toString(arr));
        reverseArray(arr);
        System.out.println("After:  " + Arrays.toString(arr));
    }
}
