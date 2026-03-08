package com.study.dsa.backtracking;

import java.util.*;

public class PermutationsPattern {

    /**
     * Problem: Generate all permutations of distinct integers
     * Time: O(n! * n), Space: O(n!)
     *
     * TODO: Implement using backtracking
     */
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // TODO: Call backtrack with empty list

        return result; // Replace with implementation
    }

    private static void backtrack(int[] nums, List<Integer> current,
                                  boolean[] used, List<List<Integer>> result) {
        // TODO: Base case: if current.size() == nums.length, add a copy of current to result and return

        // TODO: Iterate over nums; skip indices where used[i] is true; otherwise mark used[i]=true,
        //       add nums[i] to current, recurse, then undo both (backtrack)
    }

    /**
     * Problem: Permutations with duplicates
     * Time: O(n! * n), Space: O(n!)
     *
     * TODO: Implement with duplicate handling
     */
    public static List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // TODO: Sort array first to handle duplicates
        // TODO: Use backtracking with duplicate checking

        return result; // Replace with implementation
    }

    /**
     * Problem: Next permutation
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement next permutation in-place
     */
    public static void nextPermutation(int[] nums) {
        // TODO: Implement next permutation in-place
        // TODO: Scan right-to-left to find the first index i where nums[i] < nums[i+1] (the "dip")
        // TODO: Find smallest element greater than nums[i] to the right
        // TODO: Swap them
        // TODO: Reverse suffix after i
    }
}
