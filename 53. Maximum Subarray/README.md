# Maximum Subarray (LeetCode 53) - Kadane's Algorithm

## 1. Problem Overview
Given an integer array `nums`, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.

* **Input:** `nums = [-2,1,-3,4,-1,2,1,-5,4]`
* **Output:** `6`
* **Explanation:** The contiguous subarray `[4,-1,2,1]` has the largest sum $= 6$.

---

## 2. Evolution of the Thought Process

### Initial Intuition & Pitfalls
* **Brute Force (O(N^3)$ or O(N^2)):**
  * Generating all possible subarrays $(i, j)$ and computing their sums.
  * *Why this is suboptimal:* With $N \le 10^5$, an $O(N^2)$ nested loop results in $10^{10}$ operations, causing **Time Limit Exceeded (TLE)**.
* **Greedy / Dynamic Programming Realization:**
  * When iterating through the array at index $i$, we only face one decision for `nums[i]`:
    1. **Extend** the existing subarray ending at index $i-1$ (`maxEnding + nums[i]`).
    2. **Start a new subarray** from `nums[i]` alone because the accumulated prefix sum from earlier was dragging the sum down (`maxEnding + nums[i] < nums[i]`).

---

## 3. Algorithmic Concepts: Applied vs. Not Applied

### Concepts Applied
* **Kadane's Algorithm (Dynamic Programming with O(1) Space):**
  * Defines state transition at index $i$:
    $$\text{maxEnding}[i] = \max(\text{nums}[i], \text{maxEnding}[i-1] + \text{nums}[i])$$
  * Keeps a running global maximum:
    $$\text{result} = \max(\text{result}, \text{maxEnding}[i])$$
* **Space Optimization:**
  * Instead of maintaining a full DP array `dp[N]`, we only need the result of the previous step. Two scalar variables (`maxEnding` and `result`) suffice.
* **All-Negative Arrays Handled Naturally:**
  * Initializing both `result` and `maxEnding` to `nums[0]` ensures correct output even if all elements are negative (e.g., `[-3, -2, -1]` returns `-1`), avoiding the bug of initializing `result = 0`.

### Concepts NOT Applied (And Why)
* **Prefix Sum Array + Two Pointers / Nested Loops (O(N) Space / O(N^2) Time):**
  * Unnecessary extra memory allocations and redundant comparisons.
* **Divide and Conquer (O(N log N)):**
  * While valid (finding max crossing subarray), it is more complex to code and has worse asymptotic time complexity compared to Kadane's linear scan.

---

## 4. Step-by-Step Algorithm Walkthrough

1. **Initialize State:**
   * Set `maxEnding = nums[0]` (maximum subarray sum ending at the current element).
   * Set `result = nums[0]` (overall global maximum subarray sum found so far).
2. **Linear Scan (from index $1$ to $N-1$):**
   * For element `nums[i]`:
     * Update `maxEnding = Math.max(maxEnding + nums[i], nums[i])`.
     * Update `result = Math.max(result, maxEnding)`.
3. **Return Result:** Return `result`.
