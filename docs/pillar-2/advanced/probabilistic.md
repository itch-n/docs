# Probabilistic Data Structures

## Learning Objectives
- Understand space-efficient probabilistic algorithms
- Implement Bloom filters and HyperLogLog
- Apply probabilistic structures in system design

## Core Structures

### Bloom Filter
```python
import hashlib
import math

class BloomFilter:
    def __init__(self, expected_elements, false_positive_rate=0.01):
        self.size = self._optimal_size(expected_elements, false_positive_rate)
        self.hash_count = self._optimal_hash_count(self.size, expected_elements)
        self.bit_array = [0] * self.size
        
    def _optimal_size(self, n, p):
        """Calculate optimal bit array size"""
        return int(-(n * math.log(p)) / (math.log(2) ** 2))
    
    def _optimal_hash_count(self, m, n):
        """Calculate optimal number of hash functions"""
        return int((m / n) * math.log(2))
    
    def _hash(self, item, seed):
        """Hash function with seed"""
        hasher = hashlib.md5()
        hasher.update(f"{item}{seed}".encode())
        return int(hasher.hexdigest(), 16) % self.size
    
    def add(self, item):
        """Add item to filter"""
        for i in range(self.hash_count):
            index = self._hash(item, i)
            self.bit_array[index] = 1
    
    def contains(self, item):
        """Check if item might be in set (no false negatives)"""
        for i in range(self.hash_count):
            index = self._hash(item, i)
            if self.bit_array[index] == 0:
                return False
        return True  # Might be false positive

# Usage example
bf = BloomFilter(expected_elements=10000, false_positive_rate=0.01)
bf.add("user123")
bf.add("user456")

print(bf.contains("user123"))  # True (definitely in set)
print(bf.contains("user789"))  # False (definitely not in set) or True (false positive)
```

### HyperLogLog (Cardinality Estimation)
```python
import hashlib
import math

class HyperLogLog:
    def __init__(self, precision=12):
        self.precision = precision
        self.m = 2 ** precision  # Number of buckets
        self.buckets = [0] * self.m
        
    def _hash(self, item):
        """Hash item to get bucket and leading zeros"""
        hasher = hashlib.sha1()
        hasher.update(str(item).encode())
        hex_hash = hasher.hexdigest()
        
        # Convert to binary
        binary = bin(int(hex_hash, 16))[2:].zfill(160)
        
        # First 'precision' bits determine bucket
        bucket = int(binary[:self.precision], 2)
        
        # Count leading zeros in remaining bits + 1
        remaining = binary[self.precision:]
        leading_zeros = len(remaining) - len(remaining.lstrip('0')) + 1
        
        return bucket, leading_zeros
    
    def add(self, item):
        """Add item to HLL"""
        bucket, leading_zeros = self._hash(item)
        self.buckets[bucket] = max(self.buckets[bucket], leading_zeros)
    
    def cardinality(self):
        """Estimate cardinality"""
        raw_estimate = (0.7213 / (1 + 1.079 / self.m)) * (self.m ** 2) / sum(2 ** (-x) for x in self.buckets)
        
        # Small range correction
        if raw_estimate <= 2.5 * self.m:
            zeros = self.buckets.count(0)
            if zeros != 0:
                return self.m * math.log(self.m / zeros)
        
        return int(raw_estimate)

# Usage example
hll = HyperLogLog(precision=10)
for i in range(10000):
    hll.add(f"user_{i}")

print(f"Estimated cardinality: {hll.cardinality()}")  # Should be close to 10000
```

### Count-Min Sketch (Frequency Estimation)
```python
import hashlib

class CountMinSketch:
    def __init__(self, width=1000, depth=5):
        self.width = width
        self.depth = depth
        self.table = [[0] * width for _ in range(depth)]
    
    def _hash(self, item, seed):
        hasher = hashlib.md5()
        hasher.update(f"{item}{seed}".encode())
        return int(hasher.hexdigest(), 16) % self.width
    
    def add(self, item, count=1):
        """Add item with given count"""
        for i in range(self.depth):
            j = self._hash(item, i)
            self.table[i][j] += count
    
    def estimate(self, item):
        """Estimate frequency of item"""
        min_count = float('inf')
        for i in range(self.depth):
            j = self._hash(item, i)
            min_count = min(min_count, self.table[i][j])
        return min_count

# Usage example
cms = CountMinSketch(width=1000, depth=5)
cms.add("user123", 5)
cms.add("user456", 3)
cms.add("user123", 2)  # Total: 7

print(f"Estimated frequency of user123: {cms.estimate('user123')}")  # Should be >= 7
```

## System Applications

### Distributed Caching
```python
# Use Bloom filter to avoid cache misses
class CacheWithBloomFilter:
    def __init__(self):
        self.cache = {}
        self.bloom_filter = BloomFilter(expected_elements=100000)
    
    def get(self, key):
        # Check bloom filter first (fast negative lookup)
        if not self.bloom_filter.contains(key):
            return None  # Definitely not in cache
        
        # Might be in cache (could be false positive)
        return self.cache.get(key)
    
    def set(self, key, value):
        self.cache[key] = value
        self.bloom_filter.add(key)
```

### Real-time Analytics
```python
# Track unique visitors with HyperLogLog
class AnalyticsService:
    def __init__(self):
        self.daily_visitors = {}
        self.page_frequencies = CountMinSketch()
    
    def track_visitor(self, date, user_id):
        if date not in self.daily_visitors:
            self.daily_visitors[date] = HyperLogLog()
        
        self.daily_visitors[date].add(user_id)
    
    def track_page_view(self, page):
        self.page_frequencies.add(page)
    
    def get_unique_visitors(self, date):
        return self.daily_visitors.get(date, HyperLogLog()).cardinality()
    
    def get_page_frequency(self, page):
        return self.page_frequencies.estimate(page)
```

## Trade-offs & Use Cases

| Structure | Space | Accuracy | Use Cases |
|-----------|-------|----------|-----------|
| **Bloom Filter** | Very Low | No false negatives | Cache optimization, duplicate detection |
| **HyperLogLog** | Very Low | ~2% error | Unique visitor counting, cardinality |
| **Count-Min Sketch** | Low | Overestimates | Heavy hitters, frequency estimation |

## Staff Engineering Considerations
- **When to use**: Large-scale systems where exact accuracy isn't critical
- **Trade-off decisions**: Space savings vs accuracy requirements
- **Operational concerns**: Monitoring error rates, tuning parameters
- **Alternative approaches**: When exact counts are required

## Resources
- **Original Papers**: Bloom (1970), Flajolet-Martin (1985), Cormode-Muthukrishnan (2005)
- **Redis Modules**: RedisBloom for production implementations
- **System Examples**: Google BigTable, Cassandra, Redis