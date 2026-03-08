package com.study.systems.storage;

import java.util.*;

/**
 * B+Tree: Self-balancing tree optimized for range queries
 *
 * Properties:
 * - All values stored in leaf nodes
 * - Leaves are linked (for range scans)
 * - Height kept minimal
 * - Order K: max K keys per node
 */
public class BPlusTree<K extends Comparable<K>, V> {

    private final int order; // Max keys per node (e.g., 4)
    private Node root;

    // Base node class
    abstract class Node {
        List<K> keys;
        Node parent;

        Node() {
            this.keys = new ArrayList<>();
        }

        abstract boolean isLeaf();
    }

    // Internal nodes: have children, no values
    class InternalNode extends Node {
        List<Node> children;

        InternalNode() {
            super();
            this.children = new ArrayList<>();
        }

        @Override
        boolean isLeaf() {
            return false;
        }
    }

    // Leaf nodes: have values, no children, linked to next leaf
    class LeafNode extends Node {
        List<V> values;
        LeafNode next; // For range scans

        LeafNode() {
            super();
            this.values = new ArrayList<>();
        }

        @Override
        boolean isLeaf() {
            return true;
        }
    }

    public BPlusTree(int order) {
        this.order = order;
        this.root = new LeafNode();
    }

    /**
     * Insert key-value pair
     * Time: O(log N)
     *
     * TODO: Implement insertion logic
     */
    public void insert(K key, V value) {
        // TODO: Call findLeaf(key) to locate the correct leaf node
        // TODO: Insert key/value into the leaf in sorted order; if leaf.keys.size() exceeds order, call splitLeaf()
        // TODO: When splitLeaf() creates a new root, update this.root accordingly
    }

    /**
     * Search for a key
     * Time: O(log N)
     *
     * TODO: Implement search logic
     */
    public V search(K key) {
        // TODO: Call findLeaf(key) to descend to the correct leaf
        // TODO: Linear-scan (or binary-search) the leaf's keys list for key; return the corresponding value, or null if not found

        return null; // Replace with actual implementation
    }

    /**
     * Range query: all values where startKey <= key <= endKey
     * Time: O(log N + results)
     *
     * TODO: Implement range query
     */
    public List<V> rangeQuery(K startKey, K endKey) {
        List<V> results = new ArrayList<>();

        // TODO: Call findLeaf(startKey) to reach the first candidate leaf
        // TODO: Walk the leaf chain via leaf.next: for each key in the leaf, add the value if key <= endKey; stop when key > endKey or leaf is null

        return results;
    }

    /**
     * Helper: Find the leaf node where key should be
     */
    private LeafNode findLeaf(K key) {
        Node current = root;

        // TODO: Traverse down using binary search at each internal node
        // Stop at the appropriate leaf

        while (!current.isLeaf()) {
            InternalNode internal = (InternalNode) current;

            // TODO: Find the child index using key.compareTo(internal.keys.get(i)): move right while key >= keys[i]
        }

        return (LeafNode) current;
    }

    /**
     * Helper: Split a full leaf node
     */
    private void splitLeaf(LeafNode leaf) {
        // TODO: Create a newLeaf; move the upper half of leaf's keys/values into it
        // TODO: Link newLeaf into the chain: newLeaf.next = leaf.next; leaf.next = newLeaf
        // TODO: Push the first key of newLeaf up to the parent internal node (create a new root if needed)
    }

    /**
     * Helper: Split a full internal node
     */
    private void splitInternal(InternalNode node) {
        // TODO: Create a newInternal; move the upper half of node's keys and children into it
        // TODO: The middle key is promoted (not copied) into the parent — remove it from node before moving
        // TODO: If node was the root, create a new root InternalNode holding only the promoted key
    }

    /**
     * Print tree structure (for debugging)
     */
    public void printTree() {
        printNode(root, 0);
    }

    private void printNode(Node node, int level) {
        String indent = "  ".repeat(level);
        System.out.println(indent + "Level " + level + ": " + node.keys);

        if (!node.isLeaf()) {
            InternalNode internal = (InternalNode) node;
            for (Node child : internal.children) {
                printNode(child, level + 1);
            }
        }
    }
}
