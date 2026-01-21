# Monitoring & Observability Basics

## 🎯 Learning Objectives
- Implement effective monitoring strategies
- Design alerting systems that reduce noise
- Understand the three pillars of observability

## 📚 Core Concepts

### The Three Pillars
1. **Metrics**: Numerical measurements over time
2. **Logs**: Discrete events with context
3. **Traces**: Request flow through distributed systems

### Key Metrics
```python
import time
from collections import defaultdict, deque

class MetricsCollector:
    def __init__(self):
        self.counters = defaultdict(int)
        self.gauges = defaultdict(float)
        self.histograms = defaultdict(list)
        self.timers = defaultdict(deque)
    
    def increment_counter(self, name, value=1):
        self.counters[name] += value
    
    def set_gauge(self, name, value):
        self.gauges[name] = value
    
    def record_timer(self, name, duration):
        # Keep last 1000 measurements for percentiles
        if len(self.timers[name]) >= 1000:
            self.timers[name].popleft()
        self.timers[name].append(duration)
```

### RED Method
- **Rate**: Requests per second
- **Errors**: Error rate percentage  
- **Duration**: Latency distribution

### USE Method
- **Utilization**: Resource usage percentage
- **Saturation**: Queue depth/wait time
- **Errors**: Error count/rate

## 🚀 Implementation Patterns
- **Health Checks**: `/health` endpoints for service status
- **Circuit Breaker Metrics**: Track failures and recovery
- **SLI/SLO Definition**: Service level indicators and objectives

## 📖 Resources
- **SRE Handbook**: Google's monitoring practices
- **Prometheus**: Metrics collection and alerting
- **Grafana**: Visualization and dashboards