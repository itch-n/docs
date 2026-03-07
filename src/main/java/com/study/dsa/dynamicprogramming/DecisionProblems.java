package com.study.dsa.dynamicprogramming;

import java.util.*;

public class DecisionProblems {

    /**
     * Problem: Coin change - minimum coins to make amount
     * Time: O(amount * n), Space: O(amount)
     *
     * TODO: Implement using DP
     */
    public static int coinChange(int[] coins, int amount) {
        // TODO: dp[i] = min coins to make amount i
        // TODO: dp[i] = min(dp[i], dp[i - coin] + 1) for each coin
        // TODO: Initialize dp[0] = 0, rest = infinity
        // TODO: Return dp[amount] or -1 if impossible

        return -1; // Replace with implementation
    }

    /**
     * Problem: Coin change II - count ways to make amount
     * Time: O(amount * n), Space: O(amount)
     *
     * TODO: Implement counting ways
     */
    public static int change(int amount, int[] coins) {
        // TODO: dp[i] = ways to make amount i
        // TODO: Implement iteration/conditional logic
        // TODO: dp[i] += dp[i - coin]

        return 0; // Replace with implementation
    }

    /**
     * Problem: Perfect squares - min perfect squares to sum to n
     * Time: O(n * sqrt(n)), Space: O(n)
     *
     * TODO: Implement using DP
     */
    public static int numSquares(int n) {
        // TODO: Similar to coin change
        // TODO: "Coins" are perfect squares: 1, 4, 9, 16, ...
        // TODO: dp[i] = min perfect squares to sum to i

        return 0; // Replace with implementation
    }

    /**
     * Problem: Partition equal subset sum
     * Time: O(n * sum), Space: O(sum)
     *
     * TODO: Implement subset sum DP
     */
    public static boolean canPartition(int[] nums) {
        // TODO: Implement iteration/conditional logic
        // TODO: Problem becomes: can we make sum/2?
        // TODO: dp[i] = can we make sum i?
        // TODO: Implement iteration/conditional logic

        return false; // Replace with implementation
    }


    // --- demo (moved from DecisionProblemsClient) ---

public static void main(String[] args) {
        System.out.println("=== Decision Problems ===\n");

        // Test 1: Coin change
        System.out.println("--- Test 1: Coin Change ---");
        int[] coins = {1, 2, 5};
        int[] amounts = {11, 3, 0, 7};

        System.out.println("Coins: " + Arrays.toString(coins));
        for (int amount : amounts) {
            int result = coinChange(coins, amount);
            System.out.printf("Amount %d: %d coins%n", amount, result);
        }

        // Test 2: Coin change II (count ways)
        System.out.println("\n--- Test 2: Coin Change II ---");
        int amount = 5;
        int[] coins2 = {1, 2, 5};

        System.out.println("Amount: " + amount);
        System.out.println("Coins: " + Arrays.toString(coins2));
        int ways = change(amount, coins2);
        System.out.println("Ways: " + ways);

        // Test 3: Perfect squares
        System.out.println("\n--- Test 3: Perfect Squares ---");
        int[] numbers = {12, 13, 1, 4, 9, 16};

        for (int n : numbers) {
            int count = numSquares(n);
            System.out.printf("n=%d: %d perfect squares%n", n, count);
        }

        // Test 4: Partition equal subset
        System.out.println("\n--- Test 4: Partition Equal Subset ---");
        int[][] arrays = {
            {1, 5, 11, 5},
            {1, 2, 3, 5},
            {1, 2, 3, 4}
        };

        for (int[] arr : arrays) {
            boolean canPartition = canPartition(arr);
            System.out.printf("Array: %s -> %s%n",
                Arrays.toString(arr), canPartition ? "YES" : "NO");
        }
    }
}
