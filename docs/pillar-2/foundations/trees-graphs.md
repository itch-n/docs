# Trees & Graphs

## 🎯 Learning Objectives
- Master tree traversal algorithms
- Implement graph algorithms (BFS, DFS, shortest path)
- Understand tree-based data structures

## 📚 Tree Fundamentals

### Binary Tree Traversals
```python
class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

def inorder_traversal(root):
    """Left -> Root -> Right"""
    result = []
    
    def inorder(node):
        if node:
            inorder(node.left)
            result.append(node.val)
            inorder(node.right)
    
    inorder(root)
    return result

def level_order_traversal(root):
    """BFS traversal"""
    if not root:
        return []
    
    result = []
    queue = [root]
    
    while queue:
        level_size = len(queue)
        level_nodes = []
        
        for _ in range(level_size):
            node = queue.pop(0)
            level_nodes.append(node.val)
            
            if node.left:
                queue.append(node.left)
            if node.right:
                queue.append(node.right)
        
        result.append(level_nodes)
    
    return result
```

## 🔧 Graph Algorithms

### Depth-First Search
```python
def dfs_recursive(graph, start, visited=None):
    if visited is None:
        visited = set()
    
    visited.add(start)
    print(start)  # Process node
    
    for neighbor in graph.get(start, []):
        if neighbor not in visited:
            dfs_recursive(graph, neighbor, visited)
    
    return visited

def dfs_iterative(graph, start):
    visited = set()
    stack = [start]
    
    while stack:
        node = stack.pop()
        if node not in visited:
            visited.add(node)
            print(node)  # Process node
            
            # Add neighbors to stack
            for neighbor in graph.get(node, []):
                if neighbor not in visited:
                    stack.append(neighbor)
    
    return visited
```

### Shortest Path Algorithms
```python
import heapq
from collections import defaultdict, deque

def dijkstra(graph, start):
    """Find shortest paths from start to all nodes"""
    distances = {node: float('inf') for node in graph}
    distances[start] = 0
    pq = [(0, start)]
    
    while pq:
        current_dist, u = heapq.heappop(pq)
        
        if current_dist > distances[u]:
            continue
            
        for v, weight in graph[u]:
            distance = current_dist + weight
            
            if distance < distances[v]:
                distances[v] = distance
                heapq.heappush(pq, (distance, v))
    
    return distances
```

## 🚀 Advanced Topics
- **Trie**: Prefix trees for string processing
- **Segment Trees**: Range queries and updates
- **Union-Find**: Disjoint set operations
- **Minimum Spanning Tree**: Kruskal's and Prim's algorithms

## 📖 Practice Problems
- Tree: Validate BST, Lowest Common Ancestor, Serialize/Deserialize
- Graph: Number of Islands, Course Schedule, Network Delay Time