package com.study.dsa.hashtables;

import java.util.*;
public class BasicHashMap {

    /**
     * Problem: Two Sum - find indices of two numbers that sum to target
     * Time: O(n), Space: O(n)
     *
     * TODO: Implement using HashMap for O(1) lookup
     */
    public static int[] twoSum(int[] nums, int target) {
        // TODO: Use a map to remember what you've seen
        // What should be stored as the key? As the value?

        return new int[] {-1, -1}; // Replace with implementation
    }

    /**
     * Problem: Count frequency of each character
     * Time: O(n), Space: O(k) where k = unique characters
     *
     * TODO: Implement frequency counter
     */
    public static Map<Character, Integer> countCharacters(String s) {
        // TODO: Track how many times each character appears
        // Consider using getOrDefault for cleaner code

        return new HashMap<>(); // Replace with implementation
    }

    /**
     * Problem: Contains duplicate - check if array has duplicates
     * Time: O(n), Space: O(n)
     *
     * TODO: Implement using HashSet
     */
    public static boolean containsDuplicate(int[] nums) {
        // TODO: Use a set to track seen elements
        // What indicates a duplicate has been found?

        return false; // Replace with implementation
    }


    // --- demo (moved from BasicHashMapClient) ---

public static void main(String[] args) {
        System.out.println("=== Basic Hash Map Operations ===\n");

        // Test 1: Two Sum
        System.out.println("--- Test 1: Two Sum ---");
        int[] nums = {2, 7, 11, 15};
        int target = 9;

        int[] result = twoSum(nums, target);
        System.out.printf("Array: %s%n", Arrays.toString(nums));
        System.out.printf("Target: %d%n", target);
        System.out.printf("Indices: %s%n", Arrays.toString(result));
        if (result[0] != -1) {
            System.out.printf("Values: %d + %d = %d%n",
                nums[result[0]], nums[result[1]], target);
        }

        // Test 2: Character frequency
        System.out.println("\n--- Test 2: Character Frequency ---");
        String[] testStrings = {"hello", "mississippi", "aabbcc"};

        for (String s : testStrings) {
            Map<Character, Integer> freq = countCharacters(s);
            System.out.printf("String: \"%s\"%n", s);
            System.out.println("Frequency: " + freq);
            System.out.println();
        }

        // Test 3: Contains duplicate
        System.out.println("--- Test 3: Contains Duplicate ---");
        int[][] testArrays = {
            {1, 2, 3, 4, 5},
            {1, 2, 3, 1},
            {1, 1, 1, 3, 3, 4, 3, 2, 4, 2}
        };

        for (int[] arr : testArrays) {
            boolean hasDup = containsDuplicate(arr);
            System.out.printf("Array: %s -> %s%n",
                Arrays.toString(arr), hasDup ? "HAS duplicates" : "NO duplicates");
        }
    }
}
