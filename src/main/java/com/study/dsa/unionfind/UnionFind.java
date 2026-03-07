package com.study.dsa.unionfind;

public class UnionFind {

    /**
     * Union-Find Data Structure
     * Time: O(α(n)) ≈ O(1) per operation with optimizations
     * Space: O(n)
     *
     * TODO: Implement with path compression and union by rank
     */
    static class DSU {
        private int[] parent;
        private int[] rank; // or size, depending on optimization
        private int components; // Track number of disjoint sets

        public DSU(int n) {
            // TODO: Initialize parent array: parent[i] = i
            // TODO: Initialize rank array: rank[i] = 0
            // TODO: Initialize components
        }

        /**
         * Find with path compression
         * Time: O(α(n)) amortized
         *
         * TODO: Implement find with path compression
         */
        public int find(int x) {
            // TODO: Implement iteration/conditional logic
            // TODO: Path compression: parent[x] = find(parent[x])
            // TODO: Return parent[x]
            return 0; // Replace with implementation
        }

        /**
         * Union by rank
         * Time: O(α(n)) amortized
         *
         * TODO: Implement union by rank
         */
        public boolean union(int x, int y) {
            // TODO: Find roots of x and y
            // TODO: Implement iteration/conditional logic
            // TODO: Attach smaller rank tree under larger rank tree
            // TODO: Implement iteration/conditional logic
            // TODO: Decrement components count
            // TODO: Return true (successful union)
            return false; // Replace with implementation
        }

        /**
         * Check if connected
         * Time: O(α(n))
         */
        public boolean connected(int x, int y) {
            // TODO: Return find(x) == find(y)
            return false; // Replace with implementation
        }

        /**
         * Get number of disjoint components
         * Time: O(1)
         */
        public int getComponents() {
            // TODO: Return components count
            return 0; // Replace with implementation
        }

        /**
         * Get size of component containing x
         * Time: O(α(n))
         */
        public int getSize(int x) {
            // TODO: Implement iteration/conditional logic
            // TODO: Otherwise, count elements with same root
            return 0; // Replace with implementation
        }
    }

    /**
     * Problem: Number of connected components in undirected graph
     * Time: O(E * α(V)), Space: O(V)
     *
     * TODO: Implement using union-find
     */
    public static int countComponents(int n, int[][] edges) {
        // TODO: Initialize DSU with n nodes
        // TODO: Implement iteration/conditional logic
        // TODO: Return number of components

        return 0; // Replace with implementation
    }


    // --- demo (moved from UnionFindClient) ---

public static void main(String[] args) {
        System.out.println("=== Union-Find ===\n");

        // Test 1: Basic operations
        System.out.println("--- Test 1: Basic Operations ---");
        DSU dsu = new DSU(10);

        System.out.println("Initial components: " + dsu.getComponents());

        // Connect some nodes
        int[][] connections = {{0, 1}, {1, 2}, {3, 4}, {5, 6}, {6, 7}};
        System.out.println("\nConnecting nodes:");
        for (int[] conn : connections) {
            boolean success = dsu.union(conn[0], conn[1]);
            System.out.printf("  union(%d, %d): %s%n", conn[0], conn[1],
                success ? "SUCCESS" : "ALREADY CONNECTED");
        }

        System.out.println("\nFinal components: " + dsu.getComponents());

        // Test connectivity
        System.out.println("\nConnectivity tests:");
        int[][] tests = {{0, 2}, {0, 3}, {3, 4}, {5, 8}};
        for (int[] test : tests) {
            boolean connected = dsu.connected(test[0], test[1]);
            System.out.printf("  connected(%d, %d): %s%n", test[0], test[1],
                connected ? "YES" : "NO");
        }

        // Test 2: Count components
        System.out.println("\n--- Test 2: Count Components ---");
        int n = 5;
        int[][] edges = {{0, 1}, {1, 2}, {3, 4}};

        System.out.println("Nodes: " + n);
        System.out.println("Edges: " + java.util.Arrays.deepToString(edges));

        int components = countComponents(n, edges);
        System.out.println("Components: " + components);
    }
}
