# Concurrency & Threading

## Learning Objectives
- Understand thread safety and synchronization
- Implement concurrent data structures
- Design systems for parallel processing

## Core Concepts

### Thread Safety Patterns

#### Mutex/Lock
```python
import threading
from collections import defaultdict

class ThreadSafeCounter:
    def __init__(self):
        self._value = 0
        self._lock = threading.Lock()
    
    def increment(self):
        with self._lock:
            self._value += 1
    
    def get_value(self):
        with self._lock:
            return self._value

# Read-Write Lock for better performance
class ReadWriteLock:
    def __init__(self):
        self._readers = 0
        self._writers = 0
        self._read_ready = threading.Condition(threading.RLock())
        self._write_ready = threading.Condition(threading.RLock())
```

#### Atomic Operations
```python
import threading
from concurrent.futures import ThreadPoolExecutor

class AtomicInteger:
    def __init__(self, value=0):
        self._value = value
        self._lock = threading.Lock()
    
    def compare_and_swap(self, expected, new_value):
        with self._lock:
            if self._value == expected:
                self._value = new_value
                return True
            return False
    
    def increment_and_get(self):
        with self._lock:
            self._value += 1
            return self._value
```

### Producer-Consumer Pattern
```python
import queue
import threading
import time

class ProducerConsumer:
    def __init__(self, max_queue_size=10):
        self.queue = queue.Queue(max_queue_size)
        self.shutdown = False
    
    def producer(self, producer_id):
        """Produce items and add to queue."""
        while not self.shutdown:
            item = f"item-{producer_id}-{time.time()}"
            try:
                self.queue.put(item, timeout=1)
                print(f"Producer {producer_id} produced: {item}")
            except queue.Full:
                print(f"Queue full, producer {producer_id} waiting...")
            
            time.sleep(0.1)
    
    def consumer(self, consumer_id):
        """Consume items from queue."""
        while not self.shutdown:
            try:
                item = self.queue.get(timeout=1)
                print(f"Consumer {consumer_id} consumed: {item}")
                
                # Simulate processing time
                time.sleep(0.2)
                
                self.queue.task_done()
            except queue.Empty:
                print(f"Queue empty, consumer {consumer_id} waiting...")

# Usage
pc = ProducerConsumer()

# Start producers and consumers
with ThreadPoolExecutor(max_workers=4) as executor:
    # 2 producers, 2 consumers
    executor.submit(pc.producer, 1)
    executor.submit(pc.producer, 2)
    executor.submit(pc.consumer, 1)
    executor.submit(pc.consumer, 2)
```

## Advanced Patterns

### Lock-Free Data Structures
```python
import threading
from typing import Optional, Any

class LockFreeQueue:
    """Simple lock-free queue using compare-and-swap."""
    
    class Node:
        def __init__(self, data=None):
            self.data = data
            self.next: Optional['Node'] = None
    
    def __init__(self):
        self.head = self.Node()  # Dummy node
        self.tail = self.head
        self._lock = threading.Lock()  # For simplicity, real implementation uses CAS
    
    def enqueue(self, item: Any):
        new_node = self.Node(item)
        
        with self._lock:  # In real implementation, use atomic operations
            self.tail.next = new_node
            self.tail = new_node
    
    def dequeue(self) -> Optional[Any]:
        with self._lock:  # In real implementation, use atomic operations
            first = self.head.next
            if first is None:
                return None
            
            self.head = first
            return first.data
```

### Thread Pool Implementation
```python
import threading
import queue
from typing import Callable, Any

class ThreadPool:
    def __init__(self, num_threads: int):
        self.num_threads = num_threads
        self.task_queue = queue.Queue()
        self.threads = []
        self.shutdown = False
        
        # Start worker threads
        for _ in range(num_threads):
            thread = threading.Thread(target=self._worker)
            thread.start()
            self.threads.append(thread)
    
    def _worker(self):
        """Worker thread main loop."""
        while not self.shutdown:
            try:
                func, args, kwargs = self.task_queue.get(timeout=1)
                try:
                    func(*args, **kwargs)
                except Exception as e:
                    print(f"Task execution failed: {e}")
                finally:
                    self.task_queue.task_done()
            except queue.Empty:
                continue
    
    def submit(self, func: Callable, *args, **kwargs):
        """Submit a task to the thread pool."""
        if not self.shutdown:
            self.task_queue.put((func, args, kwargs))
    
    def shutdown_pool(self):
        """Gracefully shutdown the thread pool."""
        self.shutdown = True
        for thread in self.threads:
            thread.join()
```

## Concurrency Challenges

### Race Conditions
```python
# Bad: Race condition
class UnsafeCounter:
    def __init__(self):
        self.count = 0
    
    def increment(self):
        # This is not atomic!
        temp = self.count  # Read
        temp += 1          # Modify  
        self.count = temp  # Write

# Good: Thread-safe
class SafeCounter:
    def __init__(self):
        self.count = 0
        self.lock = threading.Lock()
    
    def increment(self):
        with self.lock:
            self.count += 1
```

### Deadlock Prevention
```python
# Bad: Potential deadlock
def transfer_funds(from_account, to_account, amount):
    from_account.lock.acquire()
    to_account.lock.acquire()
    
    # Transfer logic here
    
    to_account.lock.release()
    from_account.lock.release()

# Good: Ordered locking prevents deadlock
def safe_transfer_funds(from_account, to_account, amount):
    # Always acquire locks in same order
    first_lock = from_account.lock if id(from_account) < id(to_account) else to_account.lock
    second_lock = to_account.lock if id(from_account) < id(to_account) else from_account.lock
    
    with first_lock:
        with second_lock:
            # Transfer logic here
            pass
```

## Performance Considerations

| Pattern | Throughput | Latency | Complexity |
|---------|------------|---------|------------|
| **Single Thread** | Low | Low | Simple |
| **Mutex/Lock** | Medium | Medium | Medium |
| **Lock-Free** | High | Low | Complex |
| **Actor Model** | High | Medium | Medium |

## Staff-Level Insights
- **When to use threads**: I/O-bound vs CPU-bound workloads
- **Alternative models**: Event loops, coroutines, actor systems
- **System-level concerns**: Context switching overhead, memory barriers
- **Debugging**: Race condition detection, deadlock analysis

## Resources
- **Java Concurrency in Practice**: Fundamental concurrency concepts
- **The Art of Multiprocessor Programming**: Advanced lock-free algorithms
- **DDIA Chapter 7**: Transactions and concurrency control