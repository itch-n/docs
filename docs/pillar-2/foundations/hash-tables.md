# Hash Tables & Hash Functions

## Learning Objectives
- Implement efficient hash tables with collision resolution
- Understand hash function properties and selection
- Apply hash tables in system design

## Core Concepts

### Hash Table Implementation
```java
import java.util.*;

public class HashTable<K, V> {
    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;
        
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private Entry<K, V>[] buckets;
    private int size;
    private int count;
    
    @SuppressWarnings("unchecked")
    public HashTable(int initialSize) {
        this.size = initialSize;
        this.count = 0;
        this.buckets = new Entry[size];
    }
    
    public HashTable() {
        this(16);
    }
    
    private int hash(K key) {
        return Math.abs(key.hashCode()) % size;
    }
    
    @SuppressWarnings("unchecked")
    private void resize() {
        // Resize when load factor > 0.75
        Entry<K, V>[] oldBuckets = buckets;
        int oldSize = size;
        
        size *= 2;
        count = 0;
        buckets = new Entry[size];
        
        // Rehash all existing items
        for (int i = 0; i < oldSize; i++) {
            Entry<K, V> entry = oldBuckets[i];
            while (entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }
    
    public void put(K key, V value) {
        if (count >= 0.75 * size) {
            resize();
        }
        
        int index = hash(key);
        Entry<K, V> entry = buckets[index];
        
        // Update existing key
        while (entry != null) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
            entry = entry.next;
        }
        
        // Add new key-value pair
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        count++;
    }
    
    public V get(K key) {
        int index = hash(key);
        Entry<K, V> entry = buckets[index];
        
        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }
        
        throw new NoSuchElementException("Key not found: " + key);
    }
}
```

## Hash Function Design
- **Uniform Distribution**: Minimize clustering
- **Deterministic**: Same input → same output
- **Efficient**: Fast computation
- **Avalanche Effect**: Small input changes → large hash changes

## Advanced Applications
- **Consistent Hashing**: Distributed systems
- **Bloom Filters**: Probabilistic membership testing
- **Hash-based Sharding**: Database partitioning

## Practice Problems
- Design HashMap, Group Anagrams, Two Sum variants