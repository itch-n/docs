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

```python
class StronglyConsistentCounter:
    def __init__(self):
        self.value = 0
        self.version = 0
        self.lock = threading.Lock()
    
    def increment(self):
        with self.lock:
            # Read-modify-write as atomic operation
            self.value += 1
            self.version += 1
            return self.value, self.version
    
    def read(self):
        with self.lock:
            return self.value, self.version
```

### Eventual Consistency
- **Definition**: System will become consistent given no new updates
- **Convergence**: All replicas eventually have the same value
- **Use Cases**: Social media feeds, DNS, web caches

```python
class EventuallyConsistentStore:
    def __init__(self, node_id):
        self.node_id = node_id
        self.data = {}
        self.vector_clock = {}
        self.peers = []
    
    def write(self, key, value):
        # Update local data and vector clock
        self.vector_clock[self.node_id] = self.vector_clock.get(self.node_id, 0) + 1
        self.data[key] = {
            'value': value,
            'timestamp': self.vector_clock.copy()
        }
        
        # Asynchronously propagate to peers
        self.gossip_update(key, value, self.vector_clock.copy())
    
    def read(self, key):
        return self.data.get(key, {}).get('value')
    
    def gossip_update(self, key, value, timestamp):
        # Send updates to peer nodes
        for peer in self.peers:
            peer.receive_update(key, value, timestamp, self.node_id)
```

## Practical Patterns

### Read-After-Write Consistency
```python
class ReadAfterWriteStore:
    def __init__(self):
        self.master = {}  # Write to master
        self.replicas = [{}, {}]  # Read from replicas
        self.user_last_write = {}  # Track user's last write timestamp
    
    def write(self, user_id, key, value):
        timestamp = time.time()
        
        # Write to master
        self.master[key] = {'value': value, 'timestamp': timestamp}
        
        # Track user's last write
        self.user_last_write[user_id] = timestamp
        
        # Asynchronously replicate
        self.replicate_to_followers(key, value, timestamp)
    
    def read(self, user_id, key):
        user_last_write = self.user_last_write.get(user_id, 0)
        
        # If user recently wrote, read from master
        if time.time() - user_last_write < 1.0:  # 1 second window
            return self.master.get(key, {}).get('value')
        
        # Otherwise, read from replica
        replica = random.choice(self.replicas)
        return replica.get(key, {}).get('value')
```

### Session Consistency
- **Guarantee**: Reads within a session see writes in order
- **Implementation**: Sticky sessions or token-based consistency
- **Use Cases**: Shopping carts, user preferences

## Advanced Patterns

### Conflict Resolution (CRDTs)
```python
# G-Counter (Grow-only Counter CRDT)
class GCounter:
    def __init__(self, node_id, nodes):
        self.node_id = node_id
        self.counts = {node: 0 for node in nodes}
    
    def increment(self):
        self.counts[self.node_id] += 1
    
    def value(self):
        return sum(self.counts.values())
    
    def merge(self, other):
        # Merge two G-Counter states
        for node in self.counts:
            self.counts[node] = max(self.counts[node], other.counts.get(node, 0))

# PN-Counter (Positive-Negative Counter)
class PNCounter:
    def __init__(self, node_id, nodes):
        self.positive = GCounter(node_id, nodes)
        self.negative = GCounter(node_id, nodes)
    
    def increment(self):
        self.positive.increment()
    
    def decrement(self):
        self.negative.increment()
    
    def value(self):
        return self.positive.value() - self.negative.value()
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