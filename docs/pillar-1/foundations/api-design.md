# API Design & REST Principles

## Learning Objectives
- Design RESTful APIs that scale
- Understand API versioning strategies
- Apply consistent naming and error handling

## Core Concepts

### REST Principles
- **Resource-oriented design**: URLs represent resources, not actions
- **HTTP methods**: GET (read), POST (create), PUT (update), DELETE (remove)
- **Stateless**: Each request contains all necessary information

### API Design Patterns

```http
# Good: Resource-oriented
GET    /users/123/orders
POST   /users/123/orders
PUT    /orders/456
DELETE /orders/456

# Avoid: Action-oriented
GET /getUserOrders?userId=123
POST /createOrderForUser
```

### Versioning Strategies
1. **URL versioning**: `/v1/users`, `/v2/users`
2. **Header versioning**: `Accept: application/vnd.api+json;version=1`
3. **Query parameter**: `/users?version=1`

## Best Practices

### Response Structure
```json
{
  "data": { ... },
  "meta": {
    "total": 100,
    "page": 1,
    "per_page": 20
  },
  "links": {
    "self": "/users?page=1",
    "next": "/users?page=2"
  }
}
```

### Error Handling
```json
{
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Invalid input data",
    "details": [
      {
        "field": "email",
        "message": "Invalid email format"
      }
    ]
  }
}
```

## Advanced Topics
- **GraphQL vs REST**: When to choose each approach
- **API Gateway patterns**: Rate limiting, authentication, transformation
- **Hypermedia (HATEOAS)**: Self-describing APIs

## Resources
- [REST API Design Guidelines](https://restfulapi.net/)
- [HTTP Status Code Guide](https://httpstatuses.com/)
- **Practice**: Design APIs for common systems (e-commerce, social media)