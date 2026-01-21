# System Design Problems

## Problem-Solving Framework

### The SCALE Method
1. **S**cope - Clarify requirements and constraints
2. **C**apacity - Estimate scale and performance needs  
3. **A**rchitecture - High-level design and components
4. **L**ogic - Deep dive into algorithms and data flow
5. **E**volve - Discuss trade-offs and future scaling

## Core Problems

### 1. URL Shortener (bit.ly)

**Requirements:**
- Shorten long URLs to 7-character codes
- 100M URLs created per day
- 10:1 read/write ratio
- 5-year data retention

**Key Design Decisions:**
```python
# Base62 encoding for short URLs
import string

class URLEncoder:
    ALPHABET = string.ascii_letters + string.digits  # 62 characters
    
    def encode(self, number):
        if number == 0:
            return self.ALPHABET[0]
        
        encoded = ""
        while number:
            number, remainder = divmod(number, len(self.ALPHABET))
            encoded = self.ALPHABET[remainder] + encoded
        return encoded
```

**Architecture Components:**
- **Load Balancer**: Distribute traffic
- **Application Servers**: Stateless URL processing
- **Database**: URL mappings (SQL vs NoSQL trade-offs)
- **Cache**: Hot URL lookups (Redis)
- **Analytics**: Click tracking and metrics

### 2. Chat System (WhatsApp/Slack)

**Requirements:**
- 1-on-1 and group messaging
- 50M daily active users
- Real-time message delivery
- Message history and search

**Key Technical Challenges:**
- **Real-time Communication**: WebSockets vs Long Polling
- **Message Ordering**: Timestamp vs Vector clocks
- **Offline Users**: Message queues and push notifications
- **Group Chat**: Fan-out strategies

**Database Schema:**
```sql
-- Users and conversations
CREATE TABLE users (id, username, last_seen);
CREATE TABLE conversations (id, type, created_at);
CREATE TABLE participants (conversation_id, user_id, joined_at);

-- Messages with partitioning
CREATE TABLE messages (
    id UUID PRIMARY KEY,
    conversation_id UUID,
    sender_id UUID,
    content TEXT,
    timestamp TIMESTAMP,
    message_type VARCHAR(50)
) PARTITION BY HASH(conversation_id);
```

### 3. News Feed (Twitter/Facebook)

**Requirements:**
- Follow/unfollow users
- Post tweets/updates  
- Generate personalized timeline
- 300M users, 200M daily active

**Feed Generation Strategies:**

| Approach | Pros | Cons | Best For |
|----------|------|------|----------|
| **Pull Model** | Storage efficient | Slow feed generation | Users with many follows |
| **Push Model** | Fast read time | Storage intensive | Users with few follows |
| **Hybrid** | Balanced approach | Complex implementation | Most real systems |

## Advanced Scenarios

### Multi-Region Deployment
- **Data replication**: Master-slave vs multi-master
- **Latency optimization**: CDN and edge caching
- **Consistency**: Eventual vs strong consistency trade-offs

### Handling Failures
- **Circuit breaker**: Prevent cascade failures
- **Graceful degradation**: Core features vs nice-to-have
- **Disaster recovery**: RTO/RPO requirements

## Success Metrics
- **Availability**: 99.9% uptime (8.77 hours downtime/year)
- **Latency**: p99 < 200ms for API calls
- **Throughput**: Handle peak load with auto-scaling

## Practice Tips
1. **Start simple**: Basic functionality first, then scale
2. **Ask questions**: Clarify ambiguous requirements
3. **Discuss trade-offs**: No perfect solutions, only trade-offs
4. **Consider costs**: Both technical and operational expenses