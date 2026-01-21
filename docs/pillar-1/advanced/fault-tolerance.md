# Fault Tolerance Patterns

## Learning Objectives
- Implement resilience patterns for distributed systems
- Design systems that gracefully handle failures
- Understand failure modes and recovery strategies

## Core Patterns

### Circuit Breaker
```python
import time
from enum import Enum

class CircuitState(Enum):
    CLOSED = "closed"
    OPEN = "open" 
    HALF_OPEN = "half_open"

class CircuitBreaker:
    def __init__(self, failure_threshold=5, timeout=60):
        self.failure_threshold = failure_threshold
        self.timeout = timeout
        self.failure_count = 0
        self.last_failure_time = None
        self.state = CircuitState.CLOSED
    
    def call(self, func, *args, **kwargs):
        if self.state == CircuitState.OPEN:
            if time.time() - self.last_failure_time > self.timeout:
                self.state = CircuitState.HALF_OPEN
            else:
                raise Exception("Circuit breaker is OPEN")
        
        try:
            result = func(*args, **kwargs)
            self.on_success()
            return result
        except Exception as e:
            self.on_failure()
            raise e
```

### Retry with Exponential Backoff
```python
import random
import time

def retry_with_backoff(func, max_retries=3, base_delay=1, max_delay=60):
    for attempt in range(max_retries + 1):
        try:
            return func()
        except Exception as e:
            if attempt == max_retries:
                raise e
            
            # Exponential backoff with jitter
            delay = min(base_delay * (2 ** attempt), max_delay)
            jitter = random.uniform(0, delay * 0.1)
            time.sleep(delay + jitter)
```

## Advanced Patterns
- **Bulkhead Pattern**: Isolate critical resources
- **Timeout Pattern**: Prevent resource exhaustion
- **Graceful Degradation**: Maintain core functionality during failures
- **Saga Pattern**: Manage distributed transactions

## Resources
- **Release It!**: Production-ready software patterns
- **Chaos Engineering**: Proactive failure testing