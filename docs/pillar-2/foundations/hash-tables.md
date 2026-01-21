# Hash Tables & Hash Functions

## Learning Objectives
- Implement efficient hash tables with collision resolution
- Understand hash function properties and selection
- Apply hash tables in system design

## Core Concepts

### Hash Table Implementation
```python
class HashTable:
    def __init__(self, initial_size=16):
        self.size = initial_size
        self.count = 0
        self.buckets = [[] for _ in range(self.size)]  # Chaining for collisions
    
    def _hash(self, key):
        """Simple hash function"""
        return hash(key) % self.size
    
    def _resize(self):
        """Resize when load factor > 0.75"""
        old_buckets = self.buckets
        self.size *= 2
        self.count = 0
        self.buckets = [[] for _ in range(self.size)]
        
        # Rehash all existing items
        for bucket in old_buckets:
            for key, value in bucket:
                self.put(key, value)
    
    def put(self, key, value):
        if self.count >= 0.75 * self.size:
            self._resize()
        
        index = self._hash(key)
        bucket = self.buckets[index]
        
        # Update existing key
        for i, (k, v) in enumerate(bucket):
            if k == key:
                bucket[i] = (key, value)
                return
        
        # Add new key-value pair
        bucket.append((key, value))
        self.count += 1
    
    def get(self, key):
        index = self._hash(key)
        bucket = self.buckets[index]
        
        for k, v in bucket:
            if k == key:
                return v
        
        raise KeyError(key)
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