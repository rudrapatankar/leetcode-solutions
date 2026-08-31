# Partition List - Two Dummy Pointers Approach

## 1. Problem Overview
Given the `head` of a singly linked list and a value `x`, partition the list such that all nodes with values less than `x` come before nodes with values greater than or equal to `x`.

The partitioning must be **stable**, meaning the original relative order of the nodes in each of the two partitions must be strictly preserved.

---

## 2. Evaluation of Initial Approaches

### Approach 1: Two Pointers from Both Ends (Inward Scan & Swap)
* **The Idea:** Place one pointer at the start searching for nodes `>= x` and another pointer at the end searching for nodes `< x`, then swap their values (similar to QuickSort array partitioning).
* **Why This Failed:**
  1. **Singly Linked Constraint:** Singly linked lists only have `.next` pointers and cannot step backwards from the end without expensive $O(n)$ rescans.
  2. **Destroys Stability:** Swapping elements across opposite ends reverses and scrambled relative positions, completely violating the requirement to preserve original ordering.

### Approach 2: Splitting into Two Chains (`< x` and `>= x`)
* **The Idea:** Distribute nodes into two distinct lists: one for nodes `< x` and one for nodes `>= x`.
* **Initial Concern:** There was hesitation that moving elements (such as placing a later `2` before an earlier `5`) would complicate maintaining the order of intermediate nodes (`4`, `3`).
* **Why This Actually Works:** Because a linked list is traversed strictly from **left to right**, simply attaching matching nodes to the end of their respective chain naturally acts as a FIFO (First-In, First-Out) queue. The relative order within each sub-list is automatically preserved without any swapping.

---

## 3. The Optimal Approach: Two Dummy Heads

### Core Intuition
Instead of modifying nodes in place or swapping values, create two independent chains as you walk through the list:
1. `lessHead` $\to$ collects all nodes with `val < x`.
2. `moreHead` $\to$ collects all nodes with `val >= x`.

At the end of traversal, connect the tail of the `less` list to the head of the `more` list.

---

## 4. Step-by-Step Algorithm

1. **Initialize Sentinel/Dummy Nodes:**
   * Create `dummyLess` and `dummyMore` to serve as fixed anchors.
   * Create two moving pointers: `lessTail` (pointing to `dummyLess`) and `moreTail` (pointing to `dummyMore`).
2. **Distribute Nodes (Single Pass):**
   * Traverse the list with pointer `curr`.
   * If `curr.val < x`, append `curr` to `lessTail` and advance `lessTail`.
   * If `curr.val >= x`, append `curr` to `moreTail` and advance `moreTail`.
   * Move `curr = curr.next`.
3. **Prevent Cycles:**
   * Set `moreTail.next = null`. This step is crucial because the last node in the `more` list might still point to an earlier `< x` node in the original sequence, which would cause an infinite cycle.
4. **Stitch Chains Together:**
   * Connect `lessTail.next = dummyMore.next`.
5. **Return Result:**
   * Return `dummyLess.next`.
