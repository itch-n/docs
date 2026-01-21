# Probabilistic Data Structures

## Learning Objectives
- Understand space-efficient probabilistic algorithms
- Implement Bloom filters and HyperLogLog
- Apply probabilistic structures in system design

## Core Structures

### Bloom Filter
```java
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class BloomFilter {
    private final int size;
    private final int hashCount;
    private final boolean[] bitArray;
    
    public BloomFilter(int expectedElements, double falsePositiveRate) {
        this.size = optimalSize(expectedElements, falsePositiveRate);
        this.hashCount = optimalHashCount(this.size, expectedElements);
        this.bitArray = new boolean[this.size];
    }
    
    private int optimalSize(int n, double p) {
        // Calculate optimal bit array size
        return (int) (-(n * Math.log(p)) / (Math.log(2) * Math.log(2)));
    }
    
    private int optimalHashCount(int m, int n) {
        // Calculate optimal number of hash functions
        return (int) ((double) m / n * Math.log(2));
    }
    
    private int hash(String item, int seed) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String input = item + seed;
            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
            
            // Convert first 4 bytes to int
            int hashValue = 0;
            for (int i = 0; i < 4 && i < hash.length; i++) {
                hashValue = (hashValue << 8) | (hash[i] & 0xFF);
            }
            
            return Math.abs(hashValue) % size;
        } catch (Exception e) {
            // Fallback to simple hash
            return Math.abs((item + seed).hashCode()) % size;
        }
    }
    
    public void add(String item) {
        // Add item to filter
        for (int i = 0; i < hashCount; i++) {
            int index = hash(item, i);
            bitArray[index] = true;
        }
    }
    
    public boolean contains(String item) {
        // Check if item might be in set (no false negatives)
        for (int i = 0; i < hashCount; i++) {
            int index = hash(item, i);
            if (!bitArray[index]) {
                return false;
            }
        }
        return true; // Might be false positive
    }
}

// Usage example
BloomFilter bf = new BloomFilter(10000, 0.01);
bf.add("user123");
bf.add("user456");

System.out.println(bf.contains("user123")); // True (definitely in set)
System.out.println(bf.contains("user789")); // False (definitely not) or True (false positive)
```

### HyperLogLog (Cardinality Estimation)
```java
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class HyperLogLog {
    private final int precision;
    private final int m; // Number of buckets
    private final int[] buckets;
    
    public HyperLogLog(int precision) {
        this.precision = precision;
        this.m = 1 << precision; // 2^precision
        this.buckets = new int[m];
    }
    
    public HyperLogLog() {
        this(12);
    }
    
    private int[] hash(String item) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            byte[] hashBytes = sha1.digest(item.getBytes(StandardCharsets.UTF_8));
            
            // Convert to binary string
            StringBuilder binary = new StringBuilder();
            for (byte b : hashBytes) {
                binary.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
            }
            
            String binaryStr = binary.toString();
            
            // First 'precision' bits determine bucket
            String bucketBits = binaryStr.substring(0, Math.min(precision, binaryStr.length()));
            int bucket = Integer.parseInt(bucketBits, 2);
            
            // Count leading zeros in remaining bits + 1
            String remaining = binaryStr.substring(Math.min(precision, binaryStr.length()));
            int leadingZeros = 1;
            for (char c : remaining.toCharArray()) {
                if (c == '0') {
                    leadingZeros++;
                } else {
                    break;
                }
            }
            
            return new int[]{bucket, leadingZeros};
        } catch (NoSuchAlgorithmException e) {
            // Fallback to simpler hash
            int hashValue = item.hashCode();
            int bucket = hashValue & ((1 << precision) - 1);
            int leadingZeros = Integer.numberOfLeadingZeros(hashValue >>> precision) + 1;
            return new int[]{bucket, leadingZeros};
        }
    }
    
    public void add(String item) {
        int[] hashResult = hash(item);
        int bucket = hashResult[0];
        int leadingZeros = hashResult[1];
        
        buckets[bucket] = Math.max(buckets[bucket], leadingZeros);
    }
    
    public long cardinality() {
        double sum = 0.0;
        for (int bucket : buckets) {
            sum += Math.pow(2.0, -bucket);
        }
        
        double rawEstimate = (0.7213 / (1 + 1.079 / m)) * (m * m) / sum;
        
        // Small range correction
        if (rawEstimate <= 2.5 * m) {
            int zeros = 0;
            for (int bucket : buckets) {
                if (bucket == 0) zeros++;
            }
            if (zeros != 0) {
                return (long) (m * Math.log((double) m / zeros));
            }
        }
        
        return Math.round(rawEstimate);
    }
}

// Usage example
HyperLogLog hll = new HyperLogLog(10);
for (int i = 0; i < 10000; i++) {
    hll.add("user_" + i);
}

System.out.println("Estimated cardinality: " + hll.cardinality()); // Should be close to 10000
```

### Count-Min Sketch (Frequency Estimation)
```java
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class CountMinSketch {
    private final int width;
    private final int depth;
    private final int[][] table;
    
    public CountMinSketch(int width, int depth) {
        this.width = width;
        this.depth = depth;
        this.table = new int[depth][width];
    }
    
    public CountMinSketch() {
        this(1000, 5);
    }
    
    private int hash(String item, int seed) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String input = item + seed;
            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
            
            // Convert first 4 bytes to int
            int hashValue = 0;
            for (int i = 0; i < 4 && i < hash.length; i++) {
                hashValue = (hashValue << 8) | (hash[i] & 0xFF);
            }
            
            return Math.abs(hashValue) % width;
        } catch (Exception e) {
            // Fallback to simple hash
            return Math.abs((item + seed).hashCode()) % width;
        }
    }
    
    public void add(String item, int count) {
        for (int i = 0; i < depth; i++) {
            int j = hash(item, i);
            table[i][j] += count;
        }
    }
    
    public void add(String item) {
        add(item, 1);
    }
    
    public int estimate(String item) {
        int minCount = Integer.MAX_VALUE;
        for (int i = 0; i < depth; i++) {
            int j = hash(item, i);
            minCount = Math.min(minCount, table[i][j]);
        }
        return minCount;
    }
}

// Usage example
CountMinSketch cms = new CountMinSketch(1000, 5);
cms.add("user123", 5);
cms.add("user456", 3);
cms.add("user123", 2); // Total: 7

System.out.println("Estimated frequency of user123: " + cms.estimate("user123")); // Should be >= 7
```

## System Applications

### Distributed Caching
```java
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

// Use Bloom filter to avoid cache misses
public class CacheWithBloomFilter<T> {
    private final Map<String, T> cache;
    private final BloomFilter bloomFilter;
    
    public CacheWithBloomFilter() {
        this.cache = new ConcurrentHashMap<>();
        this.bloomFilter = new BloomFilter(100000, 0.01);
    }
    
    public T get(String key) {
        // Check bloom filter first (fast negative lookup)
        if (!bloomFilter.contains(key)) {
            return null; // Definitely not in cache
        }
        
        // Might be in cache (could be false positive)
        return cache.get(key);
    }
    
    public void set(String key, T value) {
        cache.put(key, value);
        bloomFilter.add(key);
    }
}
```

### Real-time Analytics
```java
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

// Track unique visitors with HyperLogLog
public class AnalyticsService {
    private final Map<String, HyperLogLog> dailyVisitors;
    private final CountMinSketch pageFrequencies;
    
    public AnalyticsService() {
        this.dailyVisitors = new ConcurrentHashMap<>();
        this.pageFrequencies = new CountMinSketch();
    }
    
    public void trackVisitor(String date, String userId) {
        HyperLogLog visitors = dailyVisitors.computeIfAbsent(date, k -> new HyperLogLog());
        visitors.add(userId);
    }
    
    public void trackPageView(String page) {
        pageFrequencies.add(page);
    }
    
    public long getUniqueVisitors(String date) {
        HyperLogLog visitors = dailyVisitors.get(date);
        return visitors != null ? visitors.cardinality() : 0;
    }
    
    public int getPageFrequency(String page) {
        return pageFrequencies.estimate(page);
    }
}
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