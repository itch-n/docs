package com.study.systems.caching;

import java.util.*;

/**
 * LRU Cache - Evicts least recently used items when full
 * Time: O(1) for get/put
 * Space: O(capacity)
 *
 * Key insight: Combine HashMap for O(1) lookup + Doubly Linked List for O(1) move/remove
 */
public class LRUCache<K, V> {

    private final int capacity;
    private final Map<K, Node<K, V>> cache;
    private final DoublyLinkedList<K, V> list;

    static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev, next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    static class DoublyLinkedList<K, V> {
        Node<K, V> head, tail;

        DoublyLinkedList() {
            // TODO: Initialize sentinel nodes for cleaner edge case handling
        }

        /**
         * Add node to front (most recently used position)
         *
         * TODO: Implement addToFront
         */
        void addToFront(Node<K, V> node) {
            // TODO: Insert node right after head
        }

        /**
         * Remove node from list
         *
         * TODO: Implement remove
         */
        void remove(Node<K, V> node) {
            // TODO: Update prev/next pointers to bypass this node
        }

        /**
         * Remove and return least recently used (node before tail)
         *
         * TODO: Implement removeLast
         */
        Node<K, V> removeLast() {
            // TODO: Remove the node closest to tail
            // Handle empty list case

            return null; // Replace
        }

        /**
         * Move existing node to front
         *
         * TODO: Implement moveToFront
         */
        void moveToFront(Node<K, V> node) {
            // TODO: Reposition node to mark it as most recently used
        }
    }

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.list = new DoublyLinkedList<>();
    }

    /**
     * Get value for key
     * Time: O(1)
     *
     * TODO: Implement get
     */
    public V get(K key) {
        // TODO: Lookup and update recency

        return null; // Replace
    }

    /**
     * Put key-value pair
     * Time: O(1)
     *
     * TODO: Implement put
     */
    public void put(K key, V value) {
        // TODO: Handle updates to existing keys
        // Handle eviction when at capacity
        // Add new entries appropriately
    }

    public int size() {
        return cache.size();
    }


    // --- demo ---

    public static void main(String[] args) {
        System.out.println("=== LRU Cache Test ===\n");
        LRUCache<String, String> cache = new LRUCache<>(3);

        cache.put("user:1", "Alice");
        cache.put("user:2", "Bob");
        cache.put("user:3", "Charlie");
        System.out.println("Cache size: " + cache.size());

        cache.get("user:1"); // Access Alice (makes it most recent)

        cache.put("user:4", "David"); // Should evict Bob (LRU)

        System.out.println("Get user:1: " + cache.get("user:1")); // Should be Alice
        System.out.println("Get user:2: " + cache.get("user:2")); // Should be null (evicted)
        System.out.println("Get user:3: " + cache.get("user:3")); // Should be Charlie
        System.out.println("Get user:4: " + cache.get("user:4")); // Should be David
    }
}
