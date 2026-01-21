# Consistency Models & Trade-offs

## Learning Objectives
- Understand different consistency models
- Design systems with appropriate consistency guarantees
- Navigate CAP theorem trade-offs in practice

## Consistency Spectrum

### Strong Consistency
- **Linearizability**: Operations appear instantaneous at some point between start and end
- **Sequential Consistency**: Operations appear in program order across all processes
- **Use Cases**: Financial systems, inventory management

```java
public class StronglyConsistentCounter {
    private int value = 0;
    private int version = 0;
    private final Object lock = new Object();
    
    public CounterResult increment() {
        synchronized (lock) {
            // Read-modify-write as atomic operation
            this.value++;
            this.version++;
            return new CounterResult(this.value, this.version);
        }
    }
    
    public CounterResult read() {
        synchronized (lock) {
            return new CounterResult(this.value, this.version);
        }
    }
}
```

### Eventual Consistency
- **Definition**: System will become consistent given no new updates
- **Convergence**: All replicas eventually have the same value
- **Use Cases**: Social media feeds, DNS, web caches

```java
public class EventuallyConsistentStore {
    private final String nodeId;
    private final Map<String, DataEntry> data;
    private final Map<String, Integer> vectorClock;
    private final List<EventuallyConsistentStore> peers;
    
    public EventuallyConsistentStore(String nodeId) {
        this.nodeId = nodeId;
        this.data = new ConcurrentHashMap<>();
        this.vectorClock = new ConcurrentHashMap<>();
        this.peers = new ArrayList<>();
    }
    
    public void write(String key, Object value) {
        // Update local data and vector clock
        int currentClock = vectorClock.getOrDefault(nodeId, 0) + 1;
        vectorClock.put(nodeId, currentClock);
        
        DataEntry entry = new DataEntry(value, new HashMap<>(vectorClock));
        data.put(key, entry);
        
        // Asynchronously propagate to peers
        gossipUpdate(key, value, new HashMap<>(vectorClock));
    }
    
    public Object read(String key) {
        DataEntry entry = data.get(key);
        return entry != null ? entry.getValue() : null;
    }
    
    private void gossipUpdate(String key, Object value, Map<String, Integer> timestamp) {
        // Send updates to peer nodes
        for (EventuallyConsistentStore peer : peers) {
            peer.receiveUpdate(key, value, timestamp, nodeId);
        }
    }
}
```

## Practical Patterns

### Read-After-Write Consistency
```java
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ReadAfterWriteStore {
    private final Map<String, DataEntry> master = new ConcurrentHashMap<>();
    private final List<Map<String, DataEntry>> replicas = List.of(
        new ConcurrentHashMap<>(),
        new ConcurrentHashMap<>()
    );
    private final Map<String, Long> userLastWrite = new ConcurrentHashMap<>();
    private final Random random = new Random();
    
    public void write(String userId, String key, Object value) {
        long timestamp = System.currentTimeMillis();
        
        // Write to master
        DataEntry entry = new DataEntry(value, timestamp);
        master.put(key, entry);
        
        // Track user's last write
        userLastWrite.put(userId, timestamp);
        
        // Asynchronously replicate
        replicateToFollowers(key, entry);
    }
    
    public Object read(String userId, String key) {
        Long lastWrite = userLastWrite.getOrDefault(userId, 0L);
        
        // If user recently wrote, read from master
        if (System.currentTimeMillis() - lastWrite < 1000) { // 1 second window
            DataEntry entry = master.get(key);
            return entry != null ? entry.getValue() : null;
        }
        
        // Otherwise, read from replica
        Map<String, DataEntry> replica = replicas.get(random.nextInt(replicas.size()));
        DataEntry entry = replica.get(key);
        return entry != null ? entry.getValue() : null;
    }
    
    private void replicateToFollowers(String key, DataEntry entry) {
        // Async replication to followers
        for (Map<String, DataEntry> replica : replicas) {
            replica.put(key, entry);
        }
    }
}
```

### Session Consistency
- **Guarantee**: Reads within a session see writes in order
- **Implementation**: Sticky sessions or token-based consistency
- **Use Cases**: Shopping carts, user preferences

## Advanced Patterns

### Conflict Resolution (CRDTs)
```java
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// G-Counter (Grow-only Counter CRDT)
public class GCounter {
    private final String nodeId;
    private final Map<String, Integer> counts;
    
    public GCounter(String nodeId, Set<String> nodes) {
        this.nodeId = nodeId;
        this.counts = new HashMap<>();
        for (String node : nodes) {
            counts.put(node, 0);
        }
    }
    
    public void increment() {
        counts.put(nodeId, counts.get(nodeId) + 1);
    }
    
    public int value() {
        return counts.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    public void merge(GCounter other) {
        // Merge two G-Counter states
        for (String node : counts.keySet()) {
            int currentCount = counts.get(node);
            int otherCount = other.counts.getOrDefault(node, 0);
            counts.put(node, Math.max(currentCount, otherCount));
        }
    }
}

// PN-Counter (Positive-Negative Counter)
public class PNCounter {
    private final GCounter positive;
    private final GCounter negative;
    
    public PNCounter(String nodeId, Set<String> nodes) {
        this.positive = new GCounter(nodeId, nodes);
        this.negative = new GCounter(nodeId, nodes);
    }
    
    public void increment() {
        positive.increment();
    }
    
    public void decrement() {
        negative.increment();
    }
    
    public int value() {
        return positive.value() - negative.value();
    }
    
    public void merge(PNCounter other) {
        positive.merge(other.positive);
        negative.merge(other.negative);
    }
}
```

## Consistency Models Comparison

| Model | Guarantees | Performance | Complexity | Use Cases |
|-------|------------|-------------|------------|-----------|
| **Strong** | Immediate consistency | Lower throughput | Simple | Banking, inventory |
| **Eventual** | Convergence over time | High throughput | Complex | Social media, DNS |
| **Session** | Per-session ordering | Medium | Medium | Web applications |
| **Monotonic Read** | Never read old values | Medium | Medium | Caching systems |

## Staff Engineering Decisions

### Choosing Consistency Models
1. **Business Requirements**: What consistency does the business actually need?
2. **User Experience**: Can users tolerate temporary inconsistency?
3. **System Constraints**: Network partitions, latency requirements
4. **Operational Complexity**: Can the team debug eventual consistency issues?

### Hybrid Approaches
- **Per-feature consistency**: Different features, different guarantees
- **Tiered storage**: Hot data (strong), cold data (eventual)
- **Geo-distributed**: Regional consistency, global eventual

## Resources
- **DDIA Chapter 5**: Replication and consistency
- **Jepsen Testing**: Real-world consistency violations
- **Amazon DynamoDB**: Tunable consistency in practice