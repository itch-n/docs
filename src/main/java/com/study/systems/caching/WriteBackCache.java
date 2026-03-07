package com.study.systems.caching;

import java.util.*;
import java.util.concurrent.*;

/**
 * Write-Back Cache - Writes go to cache immediately, database asynchronously
 *
 * Pros: Lower write latency
 * Cons: Risk of data loss, more complex
 */
public class WriteBackCache<K, V> {

    private final LRUCache<K, V> cache;
    private final Database<K, V> database;
    private final Map<K, V> dirtyEntries;
    private final ScheduledExecutorService flusher;

    interface Database<K, V> {
        V read(K key);
        void write(K key, V value);
    }

    public WriteBackCache(int capacity, Database<K, V> database, long flushIntervalMs) {
        this.cache = new LRUCache<>(capacity);
        this.database = database;
        this.dirtyEntries = new ConcurrentHashMap<>();
        this.flusher = Executors.newSingleThreadScheduledExecutor();

        // TODO: Schedule background flush task
    }

    /**
     * Get value
     *
     * TODO: Implement get
     */
    public V get(K key) {
        // TODO: Check cache, dirty entries, then database

        return null; // Replace
    }

    /**
     * Put value
     *
     * TODO: Implement write-back
     */
    public void put(K key, V value) {
        // TODO: Update cache immediately
        // Mark for later database flush
    }

    /**
     * Flush dirty entries to database
     *
     * TODO: Implement flush
     */
    private void flushDirtyEntries() {
        // TODO: Write all dirty entries to database
        // Handle failures appropriately
    }

    public void shutdown() {
        // TODO: Ensure all data is flushed before shutdown
    }

    // --- demo ---

    static class MockDatabase<K, V> implements Database<K, V> {
        final Map<K, V> storage = new ConcurrentHashMap<>();

        @Override
        public V read(K key) {
            System.out.println("  [DB READ] " + key);
            return storage.get(key);
        }

        @Override
        public void write(K key, V value) {
            System.out.println("  [DB WRITE] " + key + " = " + value);
            storage.put(key, value);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Write-Back Cache Test ===\n");

        MockDatabase<String, String> db = new MockDatabase<>();
        WriteBackCache<String, String> cache = new WriteBackCache<>(3, db, 500);

        System.out.println("Put user:1 (write to cache only, not DB yet)");
        cache.put("user:1", "Alice");

        System.out.println("Put user:2");
        cache.put("user:2", "Bob");

        System.out.println("\nGet user:1: " + cache.get("user:1")); // Cache hit
        System.out.println("DB has user:1: " + db.storage.get("user:1")); // Not yet flushed

        System.out.println("\nWaiting for background flush...");
        Thread.sleep(700);

        System.out.println("DB has user:1 after flush: " + db.storage.get("user:1"));
        System.out.println("DB has user:2 after flush: " + db.storage.get("user:2"));

        cache.shutdown();
    }
}
