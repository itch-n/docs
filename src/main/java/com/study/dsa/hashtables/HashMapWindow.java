package com.study.dsa.hashtables;

import java.util.*;

public class HashMapWindow {

    /**
     * Problem: Minimum window substring containing all chars of target
     * Time: O(n + m), Space: O(k) where k = unique chars
     *
     * TODO: Implement using HashMap to track frequencies
     */
    public static String minWindow(String s, String t) {
        // TODO: Track character frequencies in the target string
        // Use a sliding window to find the minimum valid window

        return ""; // Replace with implementation
    }

    /**
     * Problem: Check if s2 contains permutation of s1
     * Time: O(n), Space: O(1) - only 26 chars
     *
     * TODO: Implement using frequency comparison
     */
    public static boolean checkInclusion(String s1, String s2) {
        // TODO: How can you detect a permutation using frequencies?
        // Consider using a fixed-size window

        return false; // Replace with implementation
    }


    // --- demo (moved from HashMapWindowClient) ---

public static void main(String[] args) {
        System.out.println("=== Hash Map Sliding Window ===\n");

        // Test 1: Minimum window substring
        System.out.println("--- Test 1: Minimum Window ---");
        String[][] testCases = {
            {"ADOBECODEBANC", "ABC"},
            {"a", "a"},
            {"a", "aa"}
        };

        for (String[] test : testCases) {
            String s = test[0];
            String t = test[1];
            String result = minWindow(s, t);
            System.out.printf("s=\"%s\", t=\"%s\" -> \"%s\"%n", s, t, result);
        }

        // Test 2: Check inclusion (permutation)
        System.out.println("\n--- Test 2: Check Inclusion ---");
        String[][] inclusionTests = {
            {"ab", "eidbaooo"},
            {"ab", "eidboaoo"},
            {"abc", "ccccbbbbaaaa"}
        };

        for (String[] test : inclusionTests) {
            String s1 = test[0];
            String s2 = test[1];
            boolean result = checkInclusion(s1, s2);
            System.out.printf("s1=\"%s\", s2=\"%s\" -> %b%n", s1, s2, result);
        }
    }
}
