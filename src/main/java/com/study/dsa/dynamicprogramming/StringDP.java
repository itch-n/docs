package com.study.dsa.dynamicprogramming;

import java.util.*;

public class StringDP {

    /**
     * Problem: Decode ways (1=A, 2=B, ..., 26=Z)
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement decode ways
     */
    public static int numDecodings(String s) {
        // TODO: dp[i] = ways to decode substring 0..i
        // TODO: Single digit: if s[i] != '0', dp[i] += dp[i-1]
        // TODO: Two digits: if 10 <= s[i-1:i] <= 26, dp[i] += dp[i-2]
        // TODO: Optimize space: only need last 2 values

        return 0; // Replace with implementation
    }

    /**
     * Problem: Word break - can string be segmented into words
     * Time: O(n^2 * m) where m = avg word length, Space: O(n)
     *
     * TODO: Implement word break
     */
    public static boolean wordBreak(String s, List<String> wordDict) {
        // TODO: dp[i] = can substring 0..i be segmented?
        // TODO: dp[i] = true if any dp[j] && s.substring(j,i) in dict
        // TODO: Use HashSet for O(1) word lookup

        return false; // Replace with implementation
    }

    /**
     * Problem: Longest increasing subsequence
     * Time: O(n^2), Space: O(n)
     *
     * TODO: Implement LIS using DP
     */
    public static int lengthOfLIS(int[] nums) {
        // TODO: dp[i] = length of LIS ending at i
        // TODO: Implement logic
        // TODO: Return max value in dp array

        return 0; // Replace with implementation
    }

    /**
     * Problem: Longest palindromic substring
     * Time: O(n^2), Space: O(n^2) or O(1) with expand from center
     *
     * TODO: Implement LPS
     */
    public static String longestPalindrome(String s) {
        // TODO: Expand around center approach (space O(1))
        // TODO: Try each position as center (odd and even length)
        // TODO: Or use DP: dp[i][j] = is substring i..j palindrome?

        return ""; // Replace with implementation
    }


    // --- demo (moved from StringDPClient) ---

public static void main(String[] args) {
        System.out.println("=== String DP ===\n");

        // Test 1: Decode ways
        System.out.println("--- Test 1: Decode Ways ---");
        String[] codes = {"12", "226", "06", "10"};

        for (String code : codes) {
            int ways = numDecodings(code);
            System.out.printf("Code \"%s\": %d ways%n", code, ways);
        }

        // Test 2: Word break
        System.out.println("\n--- Test 2: Word Break ---");
        List<String> dict = Arrays.asList("leet", "code", "sand", "and", "cat");
        String[] testStrings = {"leetcode", "catsand", "catsandog"};

        System.out.println("Dictionary: " + dict);
        for (String s : testStrings) {
            boolean canBreak = wordBreak(s, dict);
            System.out.printf("String \"%s\": %s%n", s, canBreak ? "YES" : "NO");
        }

        // Test 3: Longest increasing subsequence
        System.out.println("\n--- Test 3: Longest Increasing Subsequence ---");
        int[][] sequences = {
            {10, 9, 2, 5, 3, 7, 101, 18},
            {0, 1, 0, 3, 2, 3},
            {7, 7, 7, 7}
        };

        for (int[] seq : sequences) {
            int length = lengthOfLIS(seq);
            System.out.printf("Array: %s -> LIS length: %d%n",
                Arrays.toString(seq), length);
        }

        // Test 4: Longest palindromic substring
        System.out.println("\n--- Test 4: Longest Palindromic Substring ---");
        String[] palindromeTests = {"babad", "cbbd", "racecar", "noon"};

        for (String s : palindromeTests) {
            String lps = longestPalindrome(s);
            System.out.printf("String \"%s\" -> LPS: \"%s\"%n", s, lps);
        }
    }
}
