package com.study.dsa.prefixsums;

import java.util.*;

public class SubarraySum {

    /**
     * Problem: Count subarrays summing to K
     * Time: O(n), Space: O(n)
     *
     * TODO: Implement using prefix sum + hashmap
     * Key insight: if prefixSum[j] - prefixSum[i] == k,
     * then nums[i..j-1] sums to k.
     * So for each j, count how many i have prefixSum[i] == prefixSum[j] - k.
     */
    public static int subarraySum(int[] nums, int k) {
        // TODO: Initialize map with {0: 1} (empty prefix has sum 0)
        // TODO: Track running prefix sum
        // TODO: For each element, check if (currentSum - k) is in map
        // TODO: Add currentSum to map

        return 0; // Replace with implementation
    }

    /**
     * Problem: Contiguous array (equal 0s and 1s)
     * Time: O(n), Space: O(n)
     *
     * TODO: Implement using prefix sum
     * Key insight: replace 0s with -1s, then problem becomes
     * "longest subarray with sum = 0"
     */
    public static int findMaxLength(int[] nums) {
        // TODO: Initialize map with {0: -1} (sum 0 seen at index -1)
        // TODO: Track running sum (treat 0 as -1)
        // TODO: If sum seen before, update maxLen using stored index
        // TODO: Otherwise, record current index for this sum

        return 0; // Replace with implementation
    }


    // --- demo (moved from SubarraySumClient) ---

public static void main(String[] args) {
        System.out.println("=== Prefix Sum + HashMap ===\n");

        // Test 1: Subarray sum equals K
        System.out.println("--- Test 1: Subarray Sum Equals K ---");
        int[][] arrays = {{1,1,1}, {1,2,3}, {-1,-1,1}};
        int[] targets = {2, 3, 0};

        for (int i = 0; i < arrays.length; i++) {
            int count = subarraySum(arrays[i], targets[i]);
            System.out.printf("Array: %s, k=%d -> %d subarrays%n",
                Arrays.toString(arrays[i]), targets[i], count);
        }

        // Test 2: Contiguous array
        System.out.println("\n--- Test 2: Contiguous Array ---");
        int[][] binArrays = {{0,1}, {0,1,0}, {0,1,0,1,1,0}};

        for (int[] arr : binArrays) {
            int maxLen = findMaxLength(arr);
            System.out.printf("Array: %s -> maxLen=%d%n",
                Arrays.toString(arr), maxLen);
        }
    }
}
