# Distributed Systems Fundamentals

## 🎯 Learning Objectives
- Understand CAP theorem implications
- Design systems for partition tolerance
- Implement consensus algorithms

## 📚 Core Concepts

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
```python
class ConsensusNode:
    def __init__(self, node_id):
        self.node_id = node_id
        self.state = "follower"  # follower, candidate, leader
        self.term = 0
        
    def start_election(self):
        self.state = "candidate"
        self.term += 1
        # Send vote requests to other nodes
        # Become leader if majority votes received
```

#### Consistent Hashing
```python
import hashlib

class ConsistentHash:
    def __init__(self, nodes=None, replicas=150):
        self.replicas = replicas
        self.ring = {}
        self.sorted_keys = []
        
        if nodes:
            for node in nodes:
                self.add_node(node)
    
    def add_node(self, node):
        for i in range(self.replicas):
            key = self.hash(f"{node}:{i}")
            self.ring[key] = node
            self.sorted_keys.append(key)
        
        self.sorted_keys.sort()
    
    def get_node(self, string_key):
        if not self.ring:
            return None
            
        key = self.hash(string_key)
        
        # Find the next node in the ring
        for ring_key in self.sorted_keys:
            if key <= ring_key:
                return self.ring[ring_key]
        
        # Wrap around to the first node
        return self.ring[self.sorted_keys[0]]
```

## 🚀 Advanced Patterns

### Saga Pattern (Distributed Transactions)
```python
class OrderSaga:
    def execute(self, order_data):
        try:
            # Step 1: Reserve inventory
            inventory_id = self.inventory_service.reserve(order_data.items)
            
            # Step 2: Process payment
            payment_id = self.payment_service.charge(order_data.payment)
            
            # Step 3: Create shipping label
            shipping_id = self.shipping_service.create_label(order_data.address)
            
            return {"status": "success", "order_id": order_id}
            
        except InventoryError:
            # No compensation needed yet
            return {"status": "failed", "reason": "insufficient_inventory"}
            
        except PaymentError:
            # Compensate: Release inventory
            self.inventory_service.release(inventory_id)
            return {"status": "failed", "reason": "payment_failed"}
            
        except ShippingError:
            # Compensate: Refund payment and release inventory
            self.payment_service.refund(payment_id)
            self.inventory_service.release(inventory_id)
            return {"status": "failed", "reason": "shipping_failed"}
```

### Event Sourcing
```python
class EventStore:
    def append_events(self, stream_id, events, expected_version):
        # Append events atomically with optimistic concurrency control
        current_version = self.get_stream_version(stream_id)
        
        if current_version != expected_version:
            raise ConcurrencyError("Stream version mismatch")
            
        for event in events:
            self.store_event(stream_id, current_version + 1, event)
            current_version += 1
    
    def get_events(self, stream_id, from_version=0):
        # Retrieve events for rebuilding aggregate state
        return self.query_events(stream_id, from_version)
```

## 🔍 Distributed System Challenges
- **Split-brain scenarios**: Multiple leaders elected
- **Clock skew**: Timestamp ordering issues  
- **Network partitions**: Handling isolated nodes
- **Cascading failures**: Circuit breaker patterns

## 📖 Resources
- **DDIA Chapters 5-9**: Distributed system fundamentals
- **Raft Consensus Algorithm**: Understandable consensus
- **Amazon DynamoDB Paper**: Highly available key-value store