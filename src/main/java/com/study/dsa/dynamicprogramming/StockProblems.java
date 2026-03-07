package com.study.dsa.dynamicprogramming;

import java.util.Arrays;

public class StockProblems {

    /**
     * Problem: Best time to buy and sell stock (one transaction)
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement single transaction
     */
    public static int maxProfit(int[] prices) {
        // TODO: Track minimum price seen so far
        // TODO: Track maximum profit (price - minPrice)
        // TODO: One pass solution

        return 0; // Replace with implementation
    }

    /**
     * Problem: Best time to buy and sell stock II (unlimited transactions)
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement unlimited transactions
     */
    public static int maxProfitUnlimited(int[] prices) {
        // TODO: Sum all positive differences
        // TODO: Buy before every increase, sell at peak

        return 0; // Replace with implementation
    }

    /**
     * Problem: Best time to buy and sell stock with cooldown
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement with cooldown
     */
    public static int maxProfitCooldown(int[] prices) {
        // TODO: Track three states:
        // TODO: Transitions: hold -> sold -> rest -> hold

        return 0; // Replace with implementation
    }

    /**
     * Problem: Best time to buy and sell stock with fee
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement with transaction fee
     */
    public static int maxProfitWithFee(int[] prices, int fee) {
        // TODO: Similar to cooldown
        // TODO: Subtract fee when selling

        return 0; // Replace with implementation
    }


    // --- demo (moved from StockProblemsClient) ---

public static void main(String[] args) {
        System.out.println("=== Stock Problems ===\n");

        // Test 1: Single transaction
        System.out.println("--- Test 1: Single Transaction ---");
        int[] prices1 = {7, 1, 5, 3, 6, 4};
        System.out.println("Prices: " + Arrays.toString(prices1));
        int profit1 = maxProfit(prices1);
        System.out.println("Max profit: " + profit1);

        // Test 2: Unlimited transactions
        System.out.println("\n--- Test 2: Unlimited Transactions ---");
        int[] prices2 = {7, 1, 5, 3, 6, 4};
        System.out.println("Prices: " + Arrays.toString(prices2));
        int profit2 = maxProfitUnlimited(prices2);
        System.out.println("Max profit: " + profit2);

        // Test 3: With cooldown
        System.out.println("\n--- Test 3: With Cooldown ---");
        int[] prices3 = {1, 2, 3, 0, 2};
        System.out.println("Prices: " + Arrays.toString(prices3));
        int profit3 = maxProfitCooldown(prices3);
        System.out.println("Max profit: " + profit3);

        // Test 4: With fee
        System.out.println("\n--- Test 4: With Transaction Fee ---");
        int[] prices4 = {1, 3, 2, 8, 4, 9};
        int fee = 2;
        System.out.println("Prices: " + Arrays.toString(prices4));
        System.out.println("Fee: " + fee);
        int profit4 = maxProfitWithFee(prices4, fee);
        System.out.println("Max profit: " + profit4);
    }
}
