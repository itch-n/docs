package com.study.dsa.unionfind;

import java.util.Arrays;

import java.util.*;

public class CycleDetection {

    /**
     * Problem: Detect if undirected graph has a cycle
     * Time: O(E * α(V)), Space: O(V)
     *
     * TODO: Implement cycle detection
     */
    public static boolean hasCycle(int n, int[][] edges) {
        // TODO: Initialize union-find
        // TODO: Implement iteration/conditional logic
        // TODO: Return false if no cycle

        return false; // Replace with implementation
    }

    /**
     * Problem: Find redundant connection (edge that creates cycle)
     * Time: O(E * α(V)), Space: O(V)
     *
     * TODO: Implement redundant connection
     */
    public static int[] findRedundantConnection(int[][] edges) {
        // TODO: Initialize union-find
        // TODO: Implement iteration/conditional logic

        return new int[]{-1, -1}; // Replace with implementation
    }

    /**
     * Problem: Check if graph is a valid tree
     * Time: O(E * α(V)), Space: O(V)
     *
     * TODO: Implement tree validation
     */
    public static boolean validTree(int n, int[][] edges) {
        // TODO: Tree must have exactly n-1 edges
        // TODO: Must have no cycles
        // TODO: Must be fully connected (1 component)

        return false; // Replace with implementation
    }

    /**
     * Problem: Find redundant directed connection
     * Time: O(E * α(V)), Space: O(V)
     *
     * TODO: Implement for directed graph
     */
    public static int[] findRedundantDirectedConnection(int[][] edges) {
        // TODO: More complex - need to handle:
        // TODO: Try removing each candidate edge

        return new int[]{-1, -1}; // Replace with implementation
    }


// --- demo (moved from CycleDetectionClient) ---

public static void main(String[] args) {
        System.out.println("=== Cycle Detection ===\n");

        // Test 1: Has cycle
        System.out.println("--- Test 1: Has Cycle ---");
        int[][][] testGraphs = {
            {{0, 1}, {1, 2}},           // No cycle
            {{0, 1}, {1, 2}, {2, 0}},   // Cycle
            {{0, 1}, {0, 2}, {1, 2}}    // Cycle
        };

        for (int i = 0; i < testGraphs.length; i++) {
            int n = 3;
            boolean cycle = hasCycle(n, testGraphs[i]);
            System.out.printf("Graph %d: %s -> %s%n", i + 1,
                Arrays.deepToString(testGraphs[i]),
                cycle ? "HAS CYCLE" : "NO CYCLE");
        }

        // Test 2: Redundant connection
        System.out.println("\n--- Test 2: Redundant Connection ---");
        int[][][] edgeSets = {
            {{1, 2}, {1, 3}, {2, 3}},
            {{1, 2}, {2, 3}, {3, 4}, {1, 4}, {1, 5}}
        };

        for (int[][] edges : edgeSets) {
            int[] redundant = findRedundantConnection(edges);
            System.out.printf("Edges: %s%n", Arrays.deepToString(edges));
            System.out.printf("Redundant: %s%n%n", Arrays.toString(redundant));
        }

        // Test 3: Valid tree
        System.out.println("--- Test 3: Valid Tree ---");
        int[][][] treeTests = {
            {{0, 1}, {0, 2}, {0, 3}, {1, 4}},           // Valid tree (5 nodes)
            {{0, 1}, {1, 2}, {2, 3}, {1, 3}, {1, 4}}    // Not tree (cycle)
        };

        for (int i = 0; i < treeTests.length; i++) {
            int n = 5;
            boolean isTree = validTree(n, treeTests[i]);
            System.out.printf("Test %d: %s -> %s%n", i + 1,
                Arrays.deepToString(treeTests[i]),
                isTree ? "VALID TREE" : "NOT TREE");
        }
    }
}
