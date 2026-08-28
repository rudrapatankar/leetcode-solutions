# Number of Provinces - Graph Traversal Approach

## Problem Overview
There are `n` cities. A province is a group of directly or indirectly connected cities. We are given an `n x n` **Adjacency Matrix** `isConnected`, where `isConnected[i][j] = 1` if city `i` and city `j` are directly connected, and `0` otherwise. Our goal is to find the total number of connected components (provinces).

## Core Graph Concepts Used

### 1. Adjacency Matrix
The graph of cities and their connections is represented as an **Adjacency Matrix**. In this 2D array format, the rows and columns represent the nodes (cities). 
* The index `i` represents the current city, and `j` represents a potential neighbor.
* If the value at `adj[i][j] == 1`, there is an edge (connection) between city `i` and city `j`. 
* We use this matrix inside our loop to check for valid paths to unvisited cities.

### 2. Depth-First Search (DFS) & Breadth-First Search (BFS)
To find a complete province, we need a traversal algorithm to explore all connected nodes. 
* **BFS (Breadth-First Search)** explores level-by-level, typically using a Queue.
* **DFS (Depth-First Search)** explores as deep as possible along each branch before backtracking, typically using Recursion.
* While your initial conceptualization leaned toward BFS, the final implemented `explore` function elegantly utilizes **DFS**. By calling `explore(adj, j, visited)` inside itself, the code dives deep into the graph, recursively "painting" every single connected city in a province before returning to the main loop.

## The Approach: "The Outer Sweep and Explore"

1. **Single Source of Truth:** Instead of manually counting connections or nodes per domain, we rely on a single boolean/integer `visited` array of size `n`. This prevents us from confusing two distinct provinces of the same size.
2. **The Outer Sweep:** We iterate through every city from `0` to `n-1` using a standard `for` loop. 
3. **Discovering a Province:** For each city `i`, we check if it has been visited. If `visited[i] == 0` (unvisited), it means we have stumbled upon a completely new, unmapped province. We immediately increment our `provinceCount`.
4. **The Exploration (DFS):** We pause the sweep and launch our `explore` function starting from city `i`. The DFS scans the adjacency matrix for that city. When it finds a connection (`1`) to an unvisited city `j`, it marks `j` as visited and recursively explores `j`'s connections. By the time this recursive chain finishes, **every single city in that specific province has been marked as visited**. 
5. When the outer loop resumes, it simply skips over all the newly visited cities, ensuring we never double-count a province.