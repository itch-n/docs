package com.study.dsa.graphs;

import java.util.*;

public class DFS {

    /**
     * Problem: Number of islands (connected components)
     * Time: O(m*n), Space: O(m*n) for recursion stack
     *
     * TODO: Implement DFS to count islands
     */
    public static int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;

        int count = 0;

        // TODO: Iterate every cell; when you find a '1', increment count and call dfs() to sink the island

        return 0; // Replace with implementation
    }

    private static void dfs(char[][] grid, int i, int j) {
        // TODO: Base case: return if i/j are out of bounds or grid[i][j] != '1'

        // TODO: Mark the current cell as visited by overwriting it with '0'

        // TODO: Recursively call dfs() on all 4 neighbors (up, down, left, right)
    }

    /**
     * Problem: Has path in graph (adjacency list)
     * Time: O(V + E), Space: O(V)
     *
     * TODO: Implement DFS path finding
     */
    public static boolean hasPath(Map<Integer, List<Integer>> graph, int start, int end) {
        Set<Integer> visited = new HashSet<>();

        // TODO: Call dfsPath with start, end, and the visited set; return its result
        return false; // Replace
    }

    private static boolean dfsPath(Map<Integer, List<Integer>> graph,
                                   int current, int target, Set<Integer> visited) {
        // TODO: If current == target, return true (base case: found the path)

        // TODO: If current is already in visited, return false (avoid revisiting)

        // TODO: Add current to visited

        // TODO: Recurse on each neighbor; if any recursive call returns true, return true immediately

        // TODO: Return false if no path found

        return false; // Replace
    }
}
