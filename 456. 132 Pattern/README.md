# 132 Pattern - Monotonic Stack Approach

## 1. Problem Overview
Given an array of `n` integers `nums`, a **132 pattern** is a subsequence of three integers `nums[i]`, `nums[j]` and `nums[k]` such that:
*   `i < j < k` (Index order)
*   `nums[i] < nums[k] < nums[j]` (Value order: Small < Medium < Large)

The goal is to return `true` if any such pattern exists, otherwise `false`.

---

## 2. Initial Failed Approaches

### Attempt 1: Left-to-Right Scanning with Simple Counters
**The Concept:** 
Traverse from left to right, using variables (`value`, `index`, `c1`, `c2`) and an `ArrayDeque` to track when the array goes up (a peak) and when it goes down (a drop).

**Why it Fundamentally Failed:**
*   **Loss of Memory (Overwriting State):** As new peaks are found, previous valid `Large` and `Medium` pairs are overwritten. If a valid `Small` number appears later, the algorithm has "forgotten" the specific pair that would have satisfied the pattern.
*   **Decoupled Logic:** Just because the array goes up (`c1 > 0`) and goes down (`c2 > 0`) somewhere in the array does not mean those specific elements align into a valid `i < j < k` order. It causes false positives.

### Attempt 2: Adjacent Element Checking
**The Concept:**
Iterating backwards but checking adjacent elements directly using `nums[i]`, `nums[i-1]`, and an index `mid`.

**Why it Fundamentally Failed:**
*   **The Contiguous Trap:** The 132 pattern requires a *subsequence*, meaning the elements can be spread far apart. Checking `nums[i-1]` and `nums[i]` forces the algorithm to only look for triplets that are physically touching each other.
*   **Example Failure:** In `[1, 9, 2, 8, 3, 7, 4]`, a valid pattern is `1` (index 0), `9` (index 1), and `4` (index 6). Adjacent checking completely misses this because the elements are disjointed.

---

## 3. The Optimal Strategy: Right-to-Left Monotonic Stack

To solve this problem in $O(n)$ time while handling widely separated elements, we must reverse our thinking and traverse from **right to left**.

### Core Intuition
1.  **`maxMed` (The Medium Value):** We need to find the absolute largest possible `Medium` number (`nums[k]`). By making `Medium` as large as possible, we maximize our chances that a future number we see on the left (`Small`) will be smaller than it.
2.  **The Stack (The Large Value):** The stack acts as a waiting room for potential `Large` numbers (`nums[j]`). It maintains a monotonic decreasing order (smallest element at the top).
3.  **The Validation:** If we find a number on the left (`nums[i]`) that is strictly less than our established `maxMed`, we return `true`.

### How It Works Atomically
*   As we traverse leftward, if `nums[i]` is greater than the top of the stack, we have found a massive new peak (a great `Large` candidate).
*   Everything currently in the stack that is smaller than this new peak is now a verified `Medium` candidate (because it is to the right of the peak, and smaller than it).
*   We **pop all of them** using a `while` loop, updating `maxMed` to be the largest one we popped.
*   Finally, we push `nums[i]` onto the stack for future comparisons.

## 4. Complexity Analysis

* **Time Complexity:** $O(n)$
  Even though there is a `while` loop inside the `for` loop, every element in the array is pushed onto the stack exactly once and popped from the stack at most once. This results in $O(n)$ operations across the entire traversal.
* **Space Complexity:** $O(n)$
  In the worst-case scenario (an array sorted in descending order, e.g., `[5, 4, 3, 2, 1]`), no elements will be popped, and the stack will store all $n$ elements.