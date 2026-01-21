# Pillar 2: Data Structures & Algorithms

## Overview

DSA forms the foundation of efficient software engineering. This pillar covers both classical computer science algorithms and system-level data structures used in production databases and distributed systems.

## Learning Path

### Foundations (Senior Engineer Level)
Core data structures and algorithmic patterns:

- **[Arrays & Strings](foundations/arrays-strings.md)** - Two pointers, sliding window, string manipulation
- **[Trees & Graphs](foundations/trees-graphs.md)** - BFS/DFS, tree traversals, graph algorithms
- **[Hash Tables](foundations/hash-tables.md)** - Hash functions, collision resolution, applications

### Advanced (Staff Engineer Level)
System-level algorithms and data structures:

- **[System Algorithms](advanced/system-algorithms.md)** - Consistent hashing, load balancing algorithms
- **[Probabilistic Structures](advanced/probabilistic.md)** - Bloom filters, HyperLogLog, Count-Min Sketch
- **[Database Internals](advanced/database-internals.md)** - B+ Trees, LSM Trees, indexing strategies

### Practice
Apply your knowledge:

- **[Algorithm Problems](practice/problems.md)** - LeetCode-style problems with system design context
- **[Optimization Challenges](practice/optimization.md)** - Performance optimization case studies

## Key Concepts

| Complexity | Foundation | Advanced |
|-----------|------------|----------|
| **Time** | Big O notation, common patterns | Amortized analysis, probabilistic bounds |
| **Space** | Memory usage, in-place algorithms | Cache efficiency, memory hierarchy |
| **Trade-offs** | Time vs Space | Accuracy vs Performance, Consistency vs Speed |

## Staff Engineer Focus

Unlike traditional algorithm interviews, staff-level DSA focuses on:

- **System Context**: How does this data structure perform in a distributed system?
- **Trade-off Analysis**: When would you choose X over Y?
- **Real-World Constraints**: Memory limits, network partitions, hardware failures

## Recommended Reading

- **DDIA Chapter 3**: Storage and Retrieval
- **Algorithm Design Manual**: Practical algorithm selection
- **Database Internals**: Modern storage engine design