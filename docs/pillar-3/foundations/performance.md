# Performance Optimization

## Learning Objectives
- Profile and identify performance bottlenecks
- Apply optimization techniques for CPU, memory, and I/O
- Understand performance trade-offs in system design

## Core Concepts

### Profiling & Measurement
```java
import java.util.function.Supplier;

public class PerformanceUtils {
    
    public static <T> T measureTime(String operationName, Supplier<T> operation) {
        long start = System.nanoTime();
        T result = operation.get();
        long end = System.nanoTime();
        double seconds = (end - start) / 1_000_000_000.0;
        System.out.printf("%s took %.4f seconds%n", operationName, seconds);
        return result;
    }
    
    public static void measureTime(String operationName, Runnable operation) {
        long start = System.nanoTime();
        operation.run();
        long end = System.nanoTime();
        double seconds = (end - start) / 1_000_000_000.0;
        System.out.printf("%s took %.4f seconds%n", operationName, seconds);
    }
}

// Usage example
public class ExpensiveOperation {
    public static long performCalculation() {
        return PerformanceUtils.measureTime("expensive_operation", () -> {
            try {
                Thread.sleep(1000); // Simulate work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            long sum = 0;
            for (int i = 0; i < 1_000_000; i++) {
                sum += i;
            }
            return sum;
        });
    }
}
```

### CPU Optimization
- **Algorithm Complexity**: Choose efficient algorithms
- **Data Structures**: Use appropriate data structures
- **Loop Optimization**: Minimize work inside loops
- **Caching**: Memoization and result caching

### Memory Optimization
- **Memory Pools**: Reduce allocation overhead
- **Data Locality**: Cache-friendly data access patterns
- **Lazy Loading**: Load data only when needed

### I/O Optimization
- **Batching**: Combine multiple operations
- **Asynchronous I/O**: Non-blocking operations
- **Connection Pooling**: Reuse database connections

## System-Level Performance
- **Load Balancing**: Distribute requests across servers
- **Caching Layers**: Redis, CDN, application cache
- **Database Optimization**: Indexing, query optimization
- **Horizontal Scaling**: Add more servers vs bigger servers

## Performance Metrics
- **Throughput**: Requests per second
- **Latency**: Response time (p50, p95, p99)
- **Resource Utilization**: CPU, memory, disk, network

## Resources
- **Systems Performance**: Brendan Gregg's methodology
- **High Performance Browser Networking**: Web performance