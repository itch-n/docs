# Pillar 1: Systems Design & Architecture

## Overview

Systems Design focuses on building scalable, reliable, and maintainable distributed systems. This pillar covers the evolution from single-service architectures to complex distributed systems that can handle millions of users.

## Learning Path

### Foundations (Senior Engineer Level)
Essential building blocks every senior engineer must master:

- **[API Design & REST](foundations/api-design.md)** - RESTful principles, versioning, documentation
- **[Database Design](foundations/database-design.md)** - Normalization, indexing, ACID properties  
- **[Caching Strategies](foundations/caching.md)** - Cache patterns, invalidation, consistency

### Advanced (Staff Engineer Level)
Complex distributed systems concepts:

- **[Distributed Systems](advanced/distributed-systems.md)** - CAP theorem, consensus algorithms
- **[Consistency Models](advanced/consistency.md)** - Eventual consistency, strong consistency trade-offs
- **[Fault Tolerance](advanced/fault-tolerance.md)** - Circuit breakers, bulkheads, graceful degradation

### Practice
Apply your knowledge:

- **[System Design Problems](practice/problems.md)** - Design Twitter, Chat systems, URL shorteners
- **[Case Studies](practice/case-studies.md)** - Real-world architecture decisions and trade-offs

## Key Concepts

| Concept | Foundation Level | Advanced Level |
|---------|------------------|----------------|
| **Scalability** | Load balancers, horizontal scaling | Sharding, microservices, service mesh |
| **Reliability** | Health checks, retries | Circuit breakers, chaos engineering |
| **Performance** | Database indexes, caching | CDNs, edge computing, partitioning |

## Recommended Reading

- **DDIA Chapter 1-4**: Foundations of data systems
- **High Scalability**: Architecture case studies
- **AWS Well-Architected Framework**: Production best practices