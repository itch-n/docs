package com.study.dsa.dynamicprogramming;

import java.util.Arrays;

public class FibonacciStyle {

    /**
     * Problem: Climbing stairs (1 or 2 steps at a time)
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement using DP
     */
    public static int climbStairs(int n) {
        // TODO: Base cases: n=1 -> 1, n=2 -> 2

        // TODO: DP approach (bottom-up):

        return 0; // Replace with implementation
    }

    /**
     * Problem: House robber (can't rob adjacent houses)
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement using DP
     */
    public static int rob(int[] nums) {
        // TODO: dp[i] = max money robbing up to house i
        // TODO: dp[i] = max(dp[i-1], dp[i-2] + nums[i])
        // TODO: Optimize space: only need last 2 values

        return 0; // Replace with implementation
    }

    /**
     * Problem: House robber II (houses in a circle)
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement circular version
     */
    public static int robCircular(int[] nums) {
        // TODO: Can't rob both first and last house
        // TODO: Try two scenarios:
        // TODO: Return max of both

        return 0; // Replace with implementation
    }

    /**
     * Problem: Min cost climbing stairs
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement min cost DP
     */
    public static int minCostClimbingStairs(int[] cost) {
        // TODO: dp[i] = min cost to reach step i
        // TODO: dp[i] = cost[i] + min(dp[i-1], dp[i-2])
        // TODO: Can start from step 0 or 1

        return 0; // Replace with implementation
    }


    // --- demo (moved from FibonacciStyleClient) ---

public static void main(String[] args) {
        System.out.println("=== Fibonacci-Style DP ===\n");

        // Test 1: Climbing stairs
        System.out.println("--- Test 1: Climbing Stairs ---");
        int[] stairs = {1, 2, 3, 4, 5, 10};
        for (int n : stairs) {
            int ways = climbStairs(n);
            System.out.printf("n=%d: %d ways%n", n, ways);
        }

        // Test 2: House robber
        System.out.println("\n--- Test 2: House Robber ---");
        int[][] houses = {
            {1, 2, 3, 1},
            {2, 7, 9, 3, 1},
            {2, 1, 1, 2}
        };

        for (int[] house : houses) {
            int maxMoney = rob(house);
            System.out.printf("Houses: %s -> Max: %d%n",
                Arrays.toString(house), maxMoney);
        }

        // Test 3: House robber II (circular)
        System.out.println("\n--- Test 3: House Robber II ---");
        int[][] circularHouses = {
            {2, 3, 2},
            {1, 2, 3, 1},
            {1, 2, 3}
        };

        for (int[] house : circularHouses) {
            int maxMoney = robCircular(house);
            System.out.printf("Houses: %s -> Max: %d%n",
                Arrays.toString(house), maxMoney);
        }

        // Test 4: Min cost climbing
        System.out.println("\n--- Test 4: Min Cost Climbing Stairs ---");
        int[][] costs = {
            {10, 15, 20},
            {1, 100, 1, 1, 1, 100, 1, 1, 100, 1}
        };

        for (int[] cost : costs) {
            int minCost = minCostClimbingStairs(cost);
            System.out.printf("Cost: %s -> Min: %d%n",
                Arrays.toString(cost), minCost);
        }
    }
}
