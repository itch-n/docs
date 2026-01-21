# Trees & Graphs

## Learning Objectives
- Master tree traversal algorithms
- Implement graph algorithms (BFS, DFS, shortest path)
- Understand tree-based data structures

## Tree Fundamentals

### Binary Tree Traversals
```java
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    
    public TreeNode() {}
    public TreeNode(int val) { this.val = val; }
    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public List<Integer> inorderTraversal(TreeNode root) {
    // Left -> Root -> Right
    List<Integer> result = new ArrayList<>();
    inorder(root, result);
    return result;
}

private void inorder(TreeNode node, List<Integer> result) {
    if (node != null) {
        inorder(node.left, result);
        result.add(node.val);
        inorder(node.right, result);
    }
}

public List<List<Integer>> levelOrderTraversal(TreeNode root) {
    // BFS traversal
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        List<Integer> levelNodes = new ArrayList<>();
        
        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            levelNodes.add(node.val);
            
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        
        result.add(levelNodes);
    }
    
    return result;
}
```

## Graph Algorithms

### Depth-First Search
```java
public Set<Integer> dfsRecursive(Map<Integer, List<Integer>> graph, int start, Set<Integer> visited) {
    if (visited == null) {
        visited = new HashSet<>();
    }
    
    visited.add(start);
    System.out.println(start); // Process node
    
    for (int neighbor : graph.getOrDefault(start, new ArrayList<>())) {
        if (!visited.contains(neighbor)) {
            dfsRecursive(graph, neighbor, visited);
        }
    }
    
    return visited;
}

public Set<Integer> dfsIterative(Map<Integer, List<Integer>> graph, int start) {
    Set<Integer> visited = new HashSet<>();
    Stack<Integer> stack = new Stack<>();
    stack.push(start);
    
    while (!stack.isEmpty()) {
        int node = stack.pop();
        if (!visited.contains(node)) {
            visited.add(node);
            System.out.println(node); // Process node
            
            // Add neighbors to stack
            for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                }
            }
        }
    }
    
    return visited;
}
```

### Shortest Path Algorithms
```java
public Map<Integer, Integer> dijkstra(Map<Integer, List<Edge>> graph, int start) {
    // Find shortest paths from start to all nodes
    Map<Integer, Integer> distances = new HashMap<>();
    PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.dist - b.dist);
    
    // Initialize distances
    for (int node : graph.keySet()) {
        distances.put(node, Integer.MAX_VALUE);
    }
    distances.put(start, 0);
    pq.offer(new Node(start, 0));
    
    while (!pq.isEmpty()) {
        Node current = pq.poll();
        
        if (current.dist > distances.get(current.id)) {
            continue;
        }
        
        for (Edge edge : graph.getOrDefault(current.id, new ArrayList<>())) {
            int distance = current.dist + edge.weight;
            
            if (distance < distances.get(edge.to)) {
                distances.put(edge.to, distance);
                pq.offer(new Node(edge.to, distance));
            }
        }
    }
    
    return distances;
}

// Helper classes
class Node {
    int id, dist;
    Node(int id, int dist) { this.id = id; this.dist = dist; }
}

class Edge {
    int to, weight;
    Edge(int to, int weight) { this.to = to; this.weight = weight; }
}
```

## Advanced Topics
- **Trie**: Prefix trees for string processing
- **Segment Trees**: Range queries and updates
- **Union-Find**: Disjoint set operations
- **Minimum Spanning Tree**: Kruskal's and Prim's algorithms

## Practice Problems
- Tree: Validate BST, Lowest Common Ancestor, Serialize/Deserialize
- Graph: Number of Islands, Course Schedule, Network Delay Time