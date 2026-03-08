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
            // TODO: Create dummy head and tail sentinel nodes; link head.next = tail and tail.prev = head
            //       so that addToFront/remove never need null checks for boundary nodes
        }

        /**
         * Add node to front (most recently used position)
         *
         * TODO: Implement addToFront
         */
        void addToFront(Node<K, V> node) {
            // TODO: Wire node between head and head.next: set node.prev=head, node.next=head.next,
            //       then update head.next.prev and head.next to point to node
        }

        /**
         * Remove node from list
         *
         * TODO: Implement remove
         */
        void remove(Node<K, V> node) {
            // TODO: Bridge the gap: set node.prev.next = node.next and node.next.prev = node.prev
        }

        /**
         * Remove and return least recently used (node before tail)
         *
         * TODO: Implement removeLast
         */
        Node<K, V> removeLast() {
            // TODO: The LRU node is tail.prev; return null if it's the head sentinel (list is empty)
            // TODO: Call remove() on that node and return it

            return null; // Replace
        }

        /**
         * Move existing node to front
         *
         * TODO: Implement moveToFront
         */
        void moveToFront(Node<K, V> node) {
            // TODO: Call remove(node) to unlink it, then call addToFront(node) to re-insert at the MRU position
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
        // TODO: Look up key in cache map; if absent return null
        // TODO: Move the found node to the front of the list (marks it as recently used), then return its value

        return null; // Replace
    }

    /**
     * Put key-value pair
     * Time: O(1)
     *
     * TODO: Implement put
     */
    public void put(K key, V value) {
        // TODO: If key already exists in cache, update its value and call moveToFront — no eviction needed
        // TODO: If at capacity, call removeLast() to get the LRU node, then remove its key from the cache map
        // TODO: Create a new Node, add it to the cache map, and call addToFront to mark it as most recently used
    }

    public int size() {
        return cache.size();
    }
}
