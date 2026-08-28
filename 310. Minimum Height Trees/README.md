# Minimum Height Trees (MHTs) - Leaf Trimming Approach

## 1. Problem Overview
We are given a tree of $n$ nodes labeled from $0$ to $n - 1$ and an array of $n - 1$ undirected edges. 
* If we choose a node as the root, the tree has a certain height.
* Our goal is to find all possible roots that give the **minimum possible tree height**.
* In any tree, there can only be **at most 2 centroids (centers)** that minimize height.

---

## 2. The Thought Process

### Initial Thought (Brute Force)
* Pick every node one by one as the root.
* Run a standard BFS/DFS starting from that root to measure the height of the tree.
* Pick the node(s) with the smallest height.
* **The Flaw:** Running a traversal for all $n$ nodes takes $O(n^2)$ time. For large trees (e.g., $n = 20,000$), this is too slow and causes a Time Limit Exceeded (TLE).

### High Degree Node is the root Misconception
* It is easy to guess that the node with the most connections (highest degree) is always the root.
* **Why this fails:** A node can have many leaves attached to it at the very edge of a long graph. Height depends on the longest path across the tree, not how many direct neighbors a single node has.

---

## 3. The Core Concept: Reverse BFS / Topological Peeling

Instead of starting from candidate roots and searching outward, we **start from the outside leaves and peel inward** toward the center (like peeling layers of an onion).

### Key Concepts Used:
1. **Degree of a Node:** The number of edges connected to it.
2. **Leaves:** Nodes with `degree == 1` (sitting on the outer boundary).
3. **Adjacency List:** An array of lists storing each node's direct neighbors.
4. **BFS Queue (Level-by-Level):** We process all current outer leaves at the same time, trim them off, and reveal the next inner layer of leaves.

---

## 4. Step-by-Step Algorithm

1. **Handle Base Cases:** If $n \le 2$, all nodes are already the answer, so return them directly.
2. **Build the Graph & Count Degrees:** Build an adjacency list and count how many edges connect to each node.
3. **Collect Initial Leaves:** Find all nodes where `degree == 1` and add them to a queue/list.
4. **Trim Layer-by-Layer:**
   * Track the total `remaining_nodes` in the tree (starting at $n$).
   * While `remaining_nodes > 2`:
     * Count how many leaves are in the current layer (`num = roots.size()`).
     * Subtract `num` from `remaining_nodes`.
     * For each of the `num` leaves, remove it and look at its neighbor.
     * Decrement the neighbor's degree by 1.
     * If the neighbor's degree drops to 1, it has become a new outer leaf for the next layer.
5. **Result:** When 2 or fewer nodes remain, stop. The remaining nodes in your queue are the centroids.
