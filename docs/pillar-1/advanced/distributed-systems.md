# Distributed Systems Fundamentals

## Learning Objectives
- Understand CAP theorem implications
- Design systems for partition tolerance
- Implement consensus algorithms

## Core Concepts

### CAP Theorem
**Cannot simultaneously guarantee all three:**
- **Consistency**: All nodes see the same data simultaneously
- **Availability**: System remains operational
- **Partition Tolerance**: System continues despite network failures

### Practical Trade-offs

| System Type | Choice | Example |
|-------------|--------|---------|
| **CP Systems** | Consistency + Partition Tolerance | Traditional databases, Zookeeper |
| **AP Systems** | Availability + Partition Tolerance | DNS, Web caches, DynamoDB |
| **CA Systems** | Consistency + Availability | Single-node databases (theoretical) |

### Distributed System Patterns

#### Leader Election
```java
public class ConsensusNode {
    private String nodeId;
    private NodeState state; // FOLLOWER, CANDIDATE, LEADER
    private int term;
    
    public ConsensusNode(String nodeId) {
        this.nodeId = nodeId;
        this.state = NodeState.FOLLOWER;
        this.term = 0;
    }
    
    public void startElection() {
        this.state = NodeState.CANDIDATE;
        this.term++;
        // Send vote requests to other nodes
        // Become leader if majority votes received
    }
}
```

#### Consistent Hashing
```java
import java.security.MessageDigest;
import java.util.*;

public class ConsistentHash {
    private final int replicas;
    private final TreeMap<Integer, String> ring;
    
    public ConsistentHash(List<String> nodes, int replicas) {
        this.replicas = replicas;
        this.ring = new TreeMap<>();
        
        if (nodes != null) {
            for (String node : nodes) {
                addNode(node);
            }
        }
    }
    
    public void addNode(String node) {
        for (int i = 0; i < replicas; i++) {
            int key = hash(node + ":" + i);
            ring.put(key, node);
        }
    }
    
    public String getNode(String stringKey) {
        if (ring.isEmpty()) {
            return null;
        }
        
        int key = hash(stringKey);
        
        // Find the next node in the ring
        Map.Entry<Integer, String> entry = ring.ceilingEntry(key);
        if (entry != null) {
            return entry.getValue();
        }
        
        // Wrap around to the first node
        return ring.firstEntry().getValue();
    }
    
    private int hash(String input) {
        return input.hashCode();
    }
}
```

## Advanced Patterns

### Saga Pattern (Distributed Transactions)
```java
public class OrderSaga {
    private InventoryService inventoryService;
    private PaymentService paymentService;
    private ShippingService shippingService;
    
    public SagaResult execute(OrderData orderData) {
        String inventoryId = null;
        String paymentId = null;
        
        try {
            // Step 1: Reserve inventory
            inventoryId = inventoryService.reserve(orderData.getItems());
            
            // Step 2: Process payment
            paymentId = paymentService.charge(orderData.getPayment());
            
            // Step 3: Create shipping label
            String shippingId = shippingService.createLabel(orderData.getAddress());
            
            return new SagaResult("success", orderId);
            
        } catch (InventoryException e) {
            // No compensation needed yet
            return new SagaResult("failed", "insufficient_inventory");
            
        } catch (PaymentException e) {
            // Compensate: Release inventory
            if (inventoryId != null) {
                inventoryService.release(inventoryId);
            }
            return new SagaResult("failed", "payment_failed");
            
        } catch (ShippingException e) {
            // Compensate: Refund payment and release inventory
            if (paymentId != null) {
                paymentService.refund(paymentId);
            }
            if (inventoryId != null) {
                inventoryService.release(inventoryId);
            }
            return new SagaResult("failed", "shipping_failed");
        }
    }
}
```

### Event Sourcing
```java
public class EventStore {
    public void appendEvents(String streamId, List<Event> events, int expectedVersion)
            throws ConcurrencyException {
        // Append events atomically with optimistic concurrency control
        int currentVersion = getStreamVersion(streamId);
        
        if (currentVersion != expectedVersion) {
            throw new ConcurrencyException("Stream version mismatch");
        }
        
        for (Event event : events) {
            storeEvent(streamId, ++currentVersion, event);
        }
    }
    
    public List<Event> getEvents(String streamId, int fromVersion) {
        // Retrieve events for rebuilding aggregate state
        return queryEvents(streamId, fromVersion);
    }
}
```

## Distributed System Challenges
- **Split-brain scenarios**: Multiple leaders elected
- **Clock skew**: Timestamp ordering issues  
- **Network partitions**: Handling isolated nodes
- **Cascading failures**: Circuit breaker patterns

## Resources
- **DDIA Chapters 5-9**: Distributed system fundamentals
- **Raft Consensus Algorithm**: Understandable consensus
- **Amazon DynamoDB Paper**: Highly available key-value store