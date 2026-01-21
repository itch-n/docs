# Caching Strategies & Implementation

## Learning Objectives
- Implement effective caching strategies
- Understand cache invalidation patterns
- Choose appropriate caching layers

## Core Concepts

### Cache Patterns

#### Cache-Aside (Lazy Loading)
```java
public User getUser(String userId) {
    // Check cache first
    User user = cache.get("user:" + userId);
    if (user != null) {
        return user;
    }
    
    // Cache miss - fetch from database
    user = db.getUser(userId);
    cache.set("user:" + userId, user, 3600); // 3600 seconds TTL
    return user;
}
```

#### Write-Through Cache
```java
public User updateUser(String userId, UserData data) {
    // Update database first
    User user = db.updateUser(userId, data);
    
    // Then update cache
    cache.set("user:" + userId, user, 3600);
    return user;
}
```

#### Write-Behind (Write-Back)
```java
public void updateUserAsync(String userId, UserData data) {
    // Update cache immediately
    cache.set("user:" + userId, data, 3600);
    
    // Queue database write for later
    queue.enqueue("update_user_db", userId, data);
}
```

## Cache Layers

### Application Cache (In-Memory)
- **Redis/Memcached**: Distributed caching
- **Local cache**: Process-level caching for frequently accessed data

### Database Query Cache
- **Query result caching**: Cache expensive query results
- **Connection pooling**: Reuse database connections

### CDN & Edge Caching
- **Static assets**: Images, CSS, JavaScript
- **API responses**: Cache at edge locations

## Invalidation Strategies

### Time-Based (TTL)
```java
// Set expiration time
cache.set("user:123", userData, 3600); // 1 hour
```

### Event-Based Invalidation
```java
public void onUserUpdate(String userId) {
    // Invalidate related cache keys
    cache.delete("user:" + userId);
    cache.delete("user_profile:" + userId);
    cache.delete("user_permissions:" + userId);
}
```

### Cache Tags
```java
// Tag-based invalidation
Set<String> tags = Set.of("user", "profile");
cache.set("user:123", userData, tags);
cache.invalidateTags(List.of("user")); // Clears all user-related cache
```

## Cache Performance Patterns

| Pattern | Hit Ratio | Latency | Complexity |
|---------|-----------|---------|------------|
| **No Cache** | 0% | High | Low |
| **Simple TTL** | 70-80% | Low | Medium |
| **Smart Invalidation** | 85-95% | Very Low | High |

## Monitoring & Metrics
- **Hit Ratio**: Cache hits / (hits + misses)
- **Latency**: p50, p95, p99 response times
- **Memory Usage**: Cache size and eviction rates

## Resources
- **Redis Documentation**: Caching patterns and best practices
- **DDIA Chapter 1**: Caching fundamentals
- **High Scalability**: Real-world caching architectures