# Monitoring & Observability Basics

## Learning Objectives
- Implement effective monitoring strategies
- Design alerting systems that reduce noise
- Understand the three pillars of observability

## Core Concepts

### The Three Pillars
1. **Metrics**: Numerical measurements over time
2. **Logs**: Discrete events with context
3. **Traces**: Request flow through distributed systems

### Key Metrics
```java
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MetricsCollector {
    private final ConcurrentHashMap<String, AtomicLong> counters;
    private final ConcurrentHashMap<String, AtomicReference<Double>> gauges;
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Double>> timers;
    private final int maxTimerSamples;
    
    public MetricsCollector() {
        this(1000);
    }
    
    public MetricsCollector(int maxTimerSamples) {
        this.counters = new ConcurrentHashMap<>();
        this.gauges = new ConcurrentHashMap<>();
        this.timers = new ConcurrentHashMap<>();
        this.maxTimerSamples = maxTimerSamples;
    }
    
    public void incrementCounter(String name, long value) {
        counters.computeIfAbsent(name, k -> new AtomicLong(0))
                .addAndGet(value);
    }
    
    public void incrementCounter(String name) {
        incrementCounter(name, 1);
    }
    
    public void setGauge(String name, double value) {
        gauges.computeIfAbsent(name, k -> new AtomicReference<>(0.0))
              .set(value);
    }
    
    public void recordTimer(String name, double duration) {
        ConcurrentLinkedQueue<Double> timerQueue =
            timers.computeIfAbsent(name, k -> new ConcurrentLinkedQueue<>());
        
        // Keep last maxTimerSamples measurements for percentiles
        if (timerQueue.size() >= maxTimerSamples) {
            timerQueue.poll();
        }
        timerQueue.offer(duration);
    }
    
    public long getCounterValue(String name) {
        AtomicLong counter = counters.get(name);
        return counter != null ? counter.get() : 0;
    }
    
    public double getGaugeValue(String name) {
        AtomicReference<Double> gauge = gauges.get(name);
        return gauge != null ? gauge.get() : 0.0;
    }
}
```

### RED Method
- **Rate**: Requests per second
- **Errors**: Error rate percentage  
- **Duration**: Latency distribution

### USE Method
- **Utilization**: Resource usage percentage
- **Saturation**: Queue depth/wait time
- **Errors**: Error count/rate

## Implementation Patterns
- **Health Checks**: `/health` endpoints for service status
- **Circuit Breaker Metrics**: Track failures and recovery
- **SLI/SLO Definition**: Service level indicators and objectives

## Resources
- **SRE Handbook**: Google's monitoring practices
- **Prometheus**: Metrics collection and alerting
- **Grafana**: Visualization and dashboards