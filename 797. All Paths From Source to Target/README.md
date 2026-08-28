# All Paths From Source to Target - Backtracking with DFS Approach

## 1. Problem Overview
We are given a **Directed Acyclic Graph (DAG)** of $n$ nodes labeled from $0$ to $n - 1$. The graph is represented as an adjacency list where `graph[i]` contains all direct neighbors reachable from node $i$. 

Our goal is to find and return **all possible paths** starting at node `0` and ending at node `n - 1` in any order.

---

## 2. Evolution of the Thought Process

### Initial Intuition & Confusions
1. **Returning Single Paths:** Initially, the goal was to have the recursive function `explore` return a path directly (`return path;`).
   * *Why this was tricky:* A single node can branch into multiple paths. A single return value cannot easily bubble multiple distinct branches back up without complicated merging logic.
2. **When to Record the Path:** There was confusion on whether to backtrack to the caller to record the path. 
   * *Resolution:* The easiest way is to pass a shared accumulator (`MasterPath`) down the call stack and take a snapshot of `path` the exact moment $x == y$ is reached.
3. **Looping Over `graph[0]` Manually:** The original draft tried looping over the starting node's neighbors in the main function.
   * *Resolution:* By initializing `path` with `[0]` and starting `explore` directly at node `0`, the recursive helper naturally handles all neighbor loops uniformly.

---

## 3. Graph Concepts: Applied vs. Not Applied

### Concepts Applied

* **Depth-First Search (DFS):** 
  * DFS explores down a single branch as deeply as possible until it reaches the target node $n - 1$ or a dead end, before unwinding.
* **Backtracking (Choose $\to$ Explore $\to$ Un-choose):** 
  * As we step into a neighbor, we **add** it to the current path list.
  * We recursively **explore** that branch.
  * Once the exploration returns, we **remove** the neighbor from the path list. This cleans up state so subsequent branches do not retain nodes from previous explorations.
* **Path Snapshotting (Defensive Copying):**
  * In Java, lists are reference types. When `x == y`, simply adding `MasterPath.add(path)` would add a reference that changes as backtracking continues. We create an isolated copy: `new ArrayList<>(path)`.

---

### Concepts NOT Applied (And Why)

* **Visited Array (`visited[]`):**
  * *Why not needed:* The input is explicitly guaranteed to be a **DAG (Directed Acyclic Graph)**. Because there are no cycles, the recursion will never get stuck in an infinite loop.
  * Furthermore, different valid paths can share the same intermediate nodes. A `visited` array would incorrectly block other paths from using previously traversed nodes.
* **Adjacency Matrix Conversion:**
  * The input is already provided as an **Adjacency List** (`int[][] graph`, where `graph[i]` holds direct neighbors). Converting this to a 2D matrix would waste time and increase space complexity to $O(n^2)$.
* **Breadth-First Search (BFS):**
  * While BFS can find all paths by storing full path lists inside the queue, DFS with backtracking is significantly more memory-efficient because it only maintains one active path in memory at any given time.

---

## 4. Step-by-Step Algorithm Walkthrough

1. **Initialize State:** 
   * Create `MasterPath` to hold all valid completed paths.
   * Create `path` and seed it with the starting node `0`.
2. **Recursive Traversal (`explore`):**
   * **Base Case:** If the current node $x$ equals the destination node $y$ ($n - 1$):
     * Add a copy of `path` to `MasterPath`.
     * Return to stop searching beyond the destination.
   * **Neighbor Exploration:** For every neighbor node `adj[x][j]`:
     * Add `adj[x][j]` to `path`.
     * Call `explore(adj, adj[x][j], y, path, MasterPath)`.
     * Remove the last element from `path` to backtrack cleanly.
3. **Return Result:** Return `MasterPath`.
