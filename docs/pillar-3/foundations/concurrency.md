# Concurrency & Threading

## Learning Objectives
- Understand thread safety and synchronization
- Implement concurrent data structures
- Design systems for parallel processing

## Core Concepts

### Thread Safety Patterns

#### Mutex/Lock
```java
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadSafeCounter {
    private int value = 0;
    private final ReentrantLock lock = new ReentrantLock();
    
    public void increment() {
        lock.lock();
        try {
            value++;
        } finally {
            lock.unlock();
        }
    }
    
    public int getValue() {
        lock.lock();
        try {
            return value;
        } finally {
            lock.unlock();
        }
    }
}

// Read-Write Lock for better performance
public class ReadWriteLockExample {
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();
    private int data = 0;
    
    public int read() {
        readLock.lock();
        try {
            return data;
        } finally {
            readLock.unlock();
        }
    }
    
    public void write(int value) {
        writeLock.lock();
        try {
            data = value;
        } finally {
            writeLock.unlock();
        }
    }
}
```

#### Atomic Operations
```java
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AtomicOperations {
    private final AtomicInteger value = new AtomicInteger(0);
    
    public boolean compareAndSwap(int expected, int newValue) {
        return value.compareAndSet(expected, newValue);
    }
    
    public int incrementAndGet() {
        return value.incrementAndGet();
    }
    
    public int getValue() {
        return value.get();
    }
}
```

### Producer-Consumer Pattern
```java
import java.util.concurrent.*;

public class ProducerConsumer {
    private final BlockingQueue<String> queue;
    private volatile boolean shutdown = false;
    
    public ProducerConsumer(int maxQueueSize) {
        this.queue = new ArrayBlockingQueue<>(maxQueueSize);
    }
    
    public void producer(int producerId) {
        // Produce items and add to queue
        while (!shutdown) {
            String item = "item-" + producerId + "-" + System.currentTimeMillis();
            try {
                if (queue.offer(item, 1, TimeUnit.SECONDS)) {
                    System.out.println("Producer " + producerId + " produced: " + item);
                } else {
                    System.out.println("Queue full, producer " + producerId + " waiting...");
                }
                
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    public void consumer(int consumerId) {
        // Consume items from queue
        while (!shutdown) {
            try {
                String item = queue.poll(1, TimeUnit.SECONDS);
                if (item != null) {
                    System.out.println("Consumer " + consumerId + " consumed: " + item);
                    
                    // Simulate processing time
                    Thread.sleep(200);
                } else {
                    System.out.println("Queue empty, consumer " + consumerId + " waiting...");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    public void shutdown() {
        this.shutdown = true;
    }
}

// Usage
ProducerConsumer pc = new ProducerConsumer(10);
ExecutorService executor = Executors.newFixedThreadPool(4);

// Start producers and consumers
executor.submit(() -> pc.producer(1));
executor.submit(() -> pc.producer(2));
executor.submit(() -> pc.consumer(1));
executor.submit(() -> pc.consumer(2));

// Shutdown after some time
// pc.shutdown();
// executor.shutdown();
```

## Advanced Patterns

### Lock-Free Data Structures
```java
import java.util.concurrent.atomic.AtomicReference;

public class LockFreeQueue<T> {
    // Simple lock-free queue using compare-and-swap
    
    private static class Node<T> {
        volatile T data;
        volatile AtomicReference<Node<T>> next = new AtomicReference<>();
        
        Node(T data) {
            this.data = data;
        }
    }
    
    private final AtomicReference<Node<T>> head;
    private final AtomicReference<Node<T>> tail;
    
    public LockFreeQueue() {
        Node<T> dummy = new Node<>(null);
        head = new AtomicReference<>(dummy);
        tail = new AtomicReference<>(dummy);
    }
    
    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        
        while (true) {
            Node<T> currentTail = tail.get();
            Node<T> tailNext = currentTail.next.get();
            
            // Check if tail is still the same
            if (currentTail == tail.get()) {
                if (tailNext == null) {
                    // Try to link new node at the end of list
                    if (currentTail.next.compareAndSet(null, newNode)) {
                        break; // Successfully linked
                    }
                } else {
                    // Try to swing tail to the next node
                    tail.compareAndSet(currentTail, tailNext);
                }
            }
        }
        
        // Try to swing tail to the new node
        tail.compareAndSet(tail.get(), newNode);
    }
    
    public T dequeue() {
        while (true) {
            Node<T> currentHead = head.get();
            Node<T> currentTail = tail.get();
            Node<T> headNext = currentHead.next.get();
            
            // Check if head is still the same
            if (currentHead == head.get()) {
                if (currentHead == currentTail) {
                    if (headNext == null) {
                        return null; // Queue is empty
                    }
                    // Try to swing tail to the next node
                    tail.compareAndSet(currentTail, headNext);
                } else {
                    if (headNext == null) {
                        continue; // Another thread modified the queue
                    }
                    
                    T data = headNext.data;
                    
                    // Try to swing head to the next node
                    if (head.compareAndSet(currentHead, headNext)) {
                        return data;
                    }
                }
            }
        }
    }
}
```

### Thread Pool Implementation
```java
import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

public class CustomThreadPool {
    private final int numThreads;
    private final BlockingQueue<Runnable> taskQueue;
    private final List<Thread> threads;
    private volatile boolean shutdown = false;
    
    public CustomThreadPool(int numThreads) {
        this.numThreads = numThreads;
        this.taskQueue = new LinkedBlockingQueue<>();
        this.threads = new ArrayList<>();
        
        // Start worker threads
        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(this::worker);
            thread.start();
            threads.add(thread);
        }
    }
    
    private void worker() {
        // Worker thread main loop
        while (!shutdown) {
            try {
                Runnable task = taskQueue.poll(1, TimeUnit.SECONDS);
                if (task != null) {
                    try {
                        task.run();
                    } catch (Exception e) {
                        System.err.println("Task execution failed: " + e.getMessage());
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    public void submit(Runnable task) {
        // Submit a task to the thread pool
        if (!shutdown) {
            try {
                taskQueue.put(task);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }
    
    public void shutdownPool() {
        // Gracefully shutdown the thread pool
        shutdown = true;
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
```

## Concurrency Challenges

### Race Conditions
```java
// Bad: Race condition
public class UnsafeCounter {
    private int count = 0;
    
    public void increment() {
        // This is not atomic!
        int temp = count;  // Read
        temp++;            // Modify
        count = temp;      // Write
    }
    
    public int getCount() {
        return count;
    }
}

// Good: Thread-safe
public class SafeCounter {
    private int count = 0;
    private final Object lock = new Object();
    
    public void increment() {
        synchronized (lock) {
            count++;
        }
    }
    
    public int getCount() {
        synchronized (lock) {
            return count;
        }
    }
}
```

### Deadlock Prevention
```java
// Bad: Potential deadlock
public void transferFunds(Account fromAccount, Account toAccount, double amount) {
    synchronized (fromAccount) {
        synchronized (toAccount) {
            // Transfer logic here
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
        }
    }
}

// Good: Ordered locking prevents deadlock
public void safeTransferFunds(Account fromAccount, Account toAccount, double amount) {
    // Always acquire locks in same order based on hash code
    Account firstLock = System.identityHashCode(fromAccount) < System.identityHashCode(toAccount)
                       ? fromAccount : toAccount;
    Account secondLock = System.identityHashCode(fromAccount) < System.identityHashCode(toAccount)
                        ? toAccount : fromAccount;
    
    synchronized (firstLock) {
        synchronized (secondLock) {
            // Transfer logic here
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
        }
    }
}
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