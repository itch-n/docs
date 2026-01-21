# Database Design & Modeling

## 🎯 Learning Objectives
- Design normalized database schemas
- Understand indexing strategies
- Choose appropriate data types and constraints

## 📚 Core Concepts

### Normalization
- **1NF**: Atomic values, no repeating groups
- **2NF**: No partial dependencies on composite keys
- **3NF**: No transitive dependencies
- **Denormalization**: Trading space for performance

### Entity-Relationship Design

```sql
-- Users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Orders table with foreign key
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(50) DEFAULT 'pending'
);
```

### Indexing Strategies
```sql
-- Primary index (automatic)
PRIMARY KEY (id)

-- Unique index for lookups
CREATE UNIQUE INDEX idx_users_email ON users(email);

-- Composite index for queries
CREATE INDEX idx_orders_user_status ON orders(user_id, status);

-- Partial index for efficiency
CREATE INDEX idx_active_orders ON orders(user_id) 
WHERE status IN ('pending', 'processing');
```

## 🔧 Best Practices

### Data Types
- Use appropriate sizes: `INT` vs `BIGINT`, `VARCHAR(255)` vs `TEXT`
- Choose correct types: `DECIMAL` for money, `TIMESTAMP` for dates
- Consider nullable vs non-null constraints

### Query Optimization
```sql
-- Good: Uses index
SELECT * FROM orders WHERE user_id = 123;

-- Bad: Forces full table scan
SELECT * FROM orders WHERE UPPER(status) = 'PENDING';

-- Good: Index-friendly
SELECT * FROM orders WHERE status = 'pending';
```

## 🚀 Advanced Topics
- **Database Sharding**: Horizontal partitioning strategies
- **Read Replicas**: Scaling read workloads
- **ACID Properties**: Consistency guarantees and trade-offs

## 📊 Common Patterns

| Pattern | Use Case | Example |
|---------|----------|---------|
| **One-to-Many** | User → Orders | `users.id` → `orders.user_id` |
| **Many-to-Many** | Users ↔ Roles | Join table `user_roles` |
| **Polymorphic** | Comments on multiple entities | `commentable_type`, `commentable_id` |

## 📖 Resources
- **DDIA Chapter 2**: Data Models and Query Languages
- [Database Design Guidelines](https://www.postgresql.org/docs/current/ddl.html)
- **Practice**: Model schemas for different domains (social media, e-commerce, SaaS)