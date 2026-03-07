package com.study.dsa.unionfind;

import java.util.*;

public class ConnectedComponents {

    /**
     * Problem: Number of islands (using union-find)
     * Time: O(m*n * α(m*n)), Space: O(m*n)
     *
     * TODO: Implement using union-find
     */
    public static int numIslands(char[][] grid) {
        // TODO: Initialize union-find for all cells
        // TODO: Implement iteration/conditional logic
        // TODO: Count unique components of land cells

        return 0; // Replace with implementation
    }

    /**
     * Problem: Number of provinces (friend circles)
     * Time: O(n^2 * α(n)), Space: O(n)
     *
     * TODO: Implement using union-find
     */
    public static int findCircleNum(int[][] isConnected) {
        // TODO: Initialize union-find with n people
        // TODO: Implement iteration/conditional logic
        // TODO: Return number of components

        return 0; // Replace with implementation
    }

    /**
     * Problem: Accounts merge (emails belonging to same person)
     * Time: O(n*k * α(n*k)), Space: O(n*k)
     *
     * TODO: Implement accounts merge
     */
    public static List<List<String>> accountsMerge(List<List<String>> accounts) {
        // TODO: Map email to account index
        // TODO: Union accounts that share emails
        // TODO: Group emails by component
        // TODO: Sort emails in each group

        return new ArrayList<>(); // Replace with implementation
    }

    /**
     * Problem: Smallest string with swaps
     * Time: O(n log n + E * α(n)), Space: O(n)
     *
     * TODO: Implement using union-find
     */
    public static String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        // TODO: Union indices that can be swapped
        // TODO: Group characters by component
        // TODO: Sort characters in each component
        // TODO: Reconstruct string

        return ""; // Replace with implementation
    }


    // --- demo (moved from ConnectedComponentsClient) ---

public static void main(String[] args) {
        System.out.println("=== Connected Components ===\n");

        // Test 1: Number of islands
        System.out.println("--- Test 1: Number of Islands ---");
        char[][] grid = {
            {'1','1','0','0','0'},
            {'1','1','0','0','0'},
            {'0','0','1','0','0'},
            {'0','0','0','1','1'}
        };

        System.out.println("Grid:");
        for (char[] row : grid) {
            System.out.println("  " + Arrays.toString(row));
        }

        int islands = numIslands(grid);
        System.out.println("Number of islands: " + islands);

        // Test 2: Number of provinces
        System.out.println("\n--- Test 2: Number of Provinces ---");
        int[][] isConnected = {
            {1, 1, 0},
            {1, 1, 0},
            {0, 0, 1}
        };

        System.out.println("Connections:");
        for (int[] row : isConnected) {
            System.out.println("  " + Arrays.toString(row));
        }

        int provinces = findCircleNum(isConnected);
        System.out.println("Number of provinces: " + provinces);

        // Test 3: Accounts merge
        System.out.println("\n--- Test 3: Accounts Merge ---");
        List<List<String>> accounts = Arrays.asList(
            Arrays.asList("John", "johnsmith@mail.com", "john00@mail.com"),
            Arrays.asList("John", "johnnybravo@mail.com"),
            Arrays.asList("John", "johnsmith@mail.com", "john_newyork@mail.com"),
            Arrays.asList("Mary", "mary@mail.com")
        );

        System.out.println("Accounts:");
        for (List<String> account : accounts) {
            System.out.println("  " + account);
        }

        List<List<String>> merged = accountsMerge(accounts);
        System.out.println("\nMerged accounts:");
        for (List<String> account : merged) {
            System.out.println("  " + account);
        }

        // Test 4: Smallest string with swaps
        System.out.println("\n--- Test 4: Smallest String with Swaps ---");
        String s = "dcab";
        List<List<Integer>> pairs = Arrays.asList(
            Arrays.asList(0, 3),
            Arrays.asList(1, 2)
        );

        System.out.println("String: " + s);
        System.out.println("Swappable pairs: " + pairs);

        String result = smallestStringWithSwaps(s, pairs);
        System.out.println("Smallest string: " + result);
    }
}
