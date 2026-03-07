package com.study.dsa.hashtables;

import java.util.*;

public class HashMapGrouping {

    /**
     * Problem: Group anagrams together
     * Time: O(n * k log k) where k = max word length, Space: O(n * k)
     *
     * TODO: Implement using HashMap with sorted string as key
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        // TODO: What makes anagrams have the same key?
        // How can you transform each string into a unique key?

        return new ArrayList<>(); // Replace with implementation
    }

    /**
     * Problem: Group numbers by digit sum
     * Time: O(n * d) where d = digits, Space: O(n)
     *
     * TODO: Implement custom grouping
     */
    public static Map<Integer, List<Integer>> groupByDigitSum(int[] nums) {
        // TODO: Compute a key for each number based on its digits
        // Group numbers with the same key together

        return new HashMap<>(); // Replace with implementation
    }

    /**
     * Problem: Find all strings that start with same character
     * Time: O(n), Space: O(n)
     *
     * TODO: Implement grouping by first character
     */
    public static Map<Character, List<String>> groupByFirstChar(String[] words) {
        // TODO: Extract the grouping criterion from each word
        // Store words with the same criterion together

        return new HashMap<>(); // Replace with implementation
    }

    // Helper: Calculate digit sum
    private static int digitSum(int n) {
        int sum = 0;
        n = Math.abs(n);
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }


    // --- demo (moved from HashMapGroupingClient) ---

public static void main(String[] args) {
        System.out.println("=== Hash Map Grouping ===\n");

        // Test 1: Group anagrams
        System.out.println("--- Test 1: Group Anagrams ---");
        String[] words = {"eat", "tea", "tan", "ate", "nat", "bat"};

        List<List<String>> groups = groupAnagrams(words);
        System.out.println("Words: " + Arrays.toString(words));
        System.out.println("Grouped:");
        for (List<String> group : groups) {
            System.out.println("  " + group);
        }

        // Test 2: Group by digit sum
        System.out.println("\n--- Test 2: Group by Digit Sum ---");
        int[] numbers = {12, 21, 13, 31, 100, 10, 1, 23, 32};

        Map<Integer, List<Integer>> digitGroups = groupByDigitSum(numbers);
        System.out.println("Numbers: " + Arrays.toString(numbers));
        System.out.println("Grouped by digit sum:");
        for (Map.Entry<Integer, List<Integer>> entry : digitGroups.entrySet()) {
            System.out.printf("  Sum %d: %s%n", entry.getKey(), entry.getValue());
        }

        // Test 3: Group by first character
        System.out.println("\n--- Test 3: Group by First Character ---");
        String[] dictionary = {"apple", "ant", "ball", "bear", "cat", "car", "dog"};

        Map<Character, List<String>> charGroups = groupByFirstChar(dictionary);
        System.out.println("Words: " + Arrays.toString(dictionary));
        System.out.println("Grouped by first character:");
        for (Map.Entry<Character, List<String>> entry : charGroups.entrySet()) {
            System.out.printf("  %c: %s%n", entry.getKey(), entry.getValue());
        }
    }
}
