# Fault Tolerance Patterns

## Learning Objectives
- Implement resilience patterns for distributed systems
- Design systems that gracefully handle failures
- Understand failure modes and recovery strategies

## Core Patterns

### Circuit Breaker
```java
public enum CircuitState {
    CLOSED, OPEN, HALF_OPEN
}

public class CircuitBreaker {
    private final int failureThreshold;
    private final long timeout;
    private int failureCount = 0;
    private long lastFailureTime = 0;
    private CircuitState state = CircuitState.CLOSED;
    
    public CircuitBreaker(int failureThreshold, long timeout) {
        this.failureThreshold = failureThreshold;
        this.timeout = timeout;
    }
    
    public <T> T call(Supplier<T> func) throws Exception {
        if (state == CircuitState.OPEN) {
            if (System.currentTimeMillis() - lastFailureTime > timeout) {
                state = CircuitState.HALF_OPEN;
            } else {
                throw new Exception("Circuit breaker is OPEN");
            }
        }
        
        try {
            T result = func.get();
            onSuccess();
            return result;
        } catch (Exception e) {
            onFailure();
            throw e;
        }
    }
    
    private void onSuccess() {
        failureCount = 0;
        state = CircuitState.CLOSED;
    }
    
    private void onFailure() {
        failureCount++;
        lastFailureTime = System.currentTimeMillis();
        if (failureCount >= failureThreshold) {
            state = CircuitState.OPEN;
        }
    }
}
```

### Retry with Exponential Backoff
```java
import java.util.Random;
import java.util.function.Supplier;

public class RetryUtils {
    private static final Random random = new Random();
    
    public static <T> T retryWithBackoff(Supplier<T> func, int maxRetries,
                                        long baseDelay, long maxDelay) throws Exception {
        Exception lastException = null;
        
        for (int attempt = 0; attempt <= maxRetries; attempt++) {
            try {
                return func.get();
            } catch (Exception e) {
                lastException = e;
                
                if (attempt == maxRetries) {
                    throw e;
                }
                
                // Exponential backoff with jitter
                long delay = Math.min(baseDelay * (1L << attempt), maxDelay);
                long jitter = (long) (random.nextDouble() * delay * 0.1);
                
                try {
                    Thread.sleep(delay + jitter);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(ie);
                }
            }
        }
        
        throw lastException;
    }
}
```

## Advanced Patterns
- **Bulkhead Pattern**: Isolate critical resources
- **Timeout Pattern**: Prevent resource exhaustion
- **Graceful Degradation**: Maintain core functionality during failures
- **Saga Pattern**: Manage distributed transactions

## Resources
- **Release It!**: Production-ready software patterns
- **Chaos Engineering**: Proactive failure testing