package com.study.dsa.graphs;

import java.util.Arrays;

import java.util.*;

public class CycleDetection {

    /**
     * Problem: Detect cycle in directed graph
     * Time: O(V + E), Space: O(V)
     *
     * TODO: Implement using DFS with states
     */
    public static boolean hasCycleDirected(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] state = new int[n]; // 0: unvisited, 1: visiting, 2: visited

        // TODO: Build graph

        // TODO: DFS from each unvisited node

        return false; // Replace
    }

    private static boolean dfsCycleDirected(List<List<Integer>> graph, int node, int[] state) {
        // TODO: If state[node] == 1, we're revisiting a node in the current DFS path — cycle found, return true

        // TODO: If state[node] == 2, this node is fully processed — no cycle here, return false

        // TODO: Mark state[node] = 1 (currently visiting / on the recursion stack)

        // TODO: Recurse on each neighbor; return true immediately if any neighbor reports a cycle

        // TODO: Mark state[node] = 2 (fully visited) before returning false

        return false; // Replace
    }

    /**
     * Problem: Detect cycle in undirected graph
     * Time: O(V + E), Space: O(V)
     *
     * TODO: Implement using DFS with parent tracking
     */
    public static boolean hasCycleUndirected(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        boolean[] visited = new boolean[n];

        // TODO: Build graph (both directions for undirected)

        // TODO: DFS from each unvisited component

        return false; // Replace
    }

    private static boolean dfsCycleUndirected(List<List<Integer>> graph, int node,
                                             int parent, boolean[] visited) {
        // TODO: Mark visited[node] = true

        // TODO: For each neighbor: skip the parent (edge we came from); if neighbor is already visited, a cycle exists
        // TODO: Otherwise recurse; propagate true up if a cycle is detected deeper

        return false; // Replace
    }
}
