# Destroy Sequential Targets - Remainder Equivalence vs. Pairwise Search

## 1. Problem Overview
Given an array of positive integers `nums` and an integer `space`, you can choose any seed target `nums[i]` to start a destruction sequence. 

Choosing `nums[i]` allows you to destroy any target in `nums` that can be represented as:

> value = nums[i] + c * space, where c >= 0

Return the value `nums[i]` that destroys the maximum number of targets. If multiple values destroy the same maximum count, return the minimum value `nums[i]`.

---

## 2. Initial Approaches & Why They Failed

### Attempt 1: Pairwise Nested Loops (O(N^2) Brute Force)
* **Idea:** For every number `nums[i]`, iterate through every other `nums[j]` to check if `(nums[j] - nums[i]) >= 0` and `(nums[j] - nums[i]) % space == 0`.
* **Why it failed:**
  1. **Time Limit Exceeded (TLE):** With N <= 10^5, N^2 ≈ 10^10 operations, exceeding LeetCode's ≈ 10^8 operations-per-second limit.
  2. **Accumulation Bug:** `count` was initialized outside the outer loop and not reset to `0` inside each iteration, causing counts to leak across targets.
  3. **Tie-Breaker Flaw:** Using `if (count > maxCount)` ignored cases where a smaller seed achieved the exact same maximum count.

### Attempt 2: Predecessor / Backward Mapping
* **Idea:** Map each element to its smaller predecessors in the sequence. Pick the element with the smallest list of predecessors (size 1) as the starting seed.
* **Why it failed:**
  1. **Sub-problem Mismatch:** The smallest element in *every* remainder group has 0 predecessors (size 1). This only measures an element's position within its own group, not the total size of that group.
  2. **Suboptimal Group Selection:** A group with size 100 has a seed of predecessor size 1, but a group with size 1 also has a seed of predecessor size 1. This strategy cannot differentiate between large and small groups.

### Attempt 3: Remainder Buckets with `HashMap<Integer, Set<Integer>>`
* **Idea:** Group numbers into sets indexed by `num % space` and find the key with the largest set size.
* **Why it failed:**
  1. **Duplicate Loss:** A `Set` discards duplicate values. If `nums = [1, 1, 1, 3]` and `space = 2`, shooting `1` destroys 4 targets, but the `Set` collapses the elements into `{1, 3}` (size 2).
  2. **Overhead & Complexity:** Storing `Set` collections adds memory overhead and requires calling `Collections.min()` across entire sets, which is slow and unnecessary.

### Attempt 4: Single `Collections.max()` on Map Entries
* **Idea:** Use `Collections.max(target.entrySet(), Map.Entry.comparingByValue())` to find the winning remainder key.
* **Why it failed:**
  1. **Arbitrary Tie Resolution:** If remainder `0` has 3 elements (minimum value `8`) and remainder `1` has 3 elements (minimum value `3`), `Collections.max()` arbitrarily picks whichever entry it encounters first. If it selects remainder `0`, it returns `8` instead of the required minimum `3`.
  2. **Syntax Errors:** 
     * `target.getValue(...)` does not exist on `HashMap` (it is `target.get(...)`).
     * `target.get(key) + 1` throws a `NullPointerException` on unseen keys.
     * `INTEGER.MAX_VALUE` is case-sensitive and must be written as `Integer.MAX_VALUE`.

---

## 3. The Optimal Approach: Remainder Equivalence & Two-Pass Scan

### Core Intuition
Two numbers X and Y belong to the same arithmetic progression family if and only if they share the exact same remainder modulo `space`:

> Y - X = c * space ⟺ Y ≡ X (mod space)

* **Group by Remainder:** Every number with the same `num % space` falls into the same equivalence class.
* **Optimal Seed:** Because c >= 0, a seed X can only destroy targets Y >= X. Therefore, picking the **minimum element** in a remainder class allows destroying **every single number** in that class.
* **Direct Frequency Lookup:** The maximum targets destroyed by any remainder group equals the total frequency of numbers having that remainder.

### Why Remainder Grouping is Better
* **Eliminates O(N^2) Pairwise Checks:** Reduces runtime from 10^10 operations to two linear scans (O(N)).
* **Preserves Duplicate Counts:** Counting raw frequencies ensures duplicate targets are properly rewarded.
* **Clean Tie-Breaking:** Resolves ties across multiple winning remainder groups by tracking the global minimum value in the second pass.

### `HashSet<Integer>` (Handling Multiple Max Keys)

| Syntax | Purpose | Example |
| :--- | :--- | :--- |
| `set.add(val)` | Inserts a remainder key into the set of winning remainders. | `maxKeysSet.add(entry.getKey());` |
| `set.clear()` | Empties the set when a strictly larger frequency is found. | `maxKeysSet.clear();` |
| `set.contains(val)` | Checks in O(1) time if a number's remainder is a top scorer. | `if (maxKeysSet.contains(num % space))` |

---

## 4. Step-by-Step Algorithm & Complexity Analysis

### Algorithm Steps

1. **Pass 1 - Count Frequencies & Track Maximum:**
   * Iterate through `nums`.
   * For each number, compute `rem = num % space`.
   * Update its frequency in a `HashMap<Integer, Integer>` using `getOrDefault(rem, 0) + 1`.
   * Keep a running variable `maxCount = Math.max(maxCount, currentCount)`.

2. **Pass 2 - Collect Winning Remainder Keys:**
   * Iterate through `map.entrySet()`.
   * If `entry.getValue() > maxValue`, reset `maxKeysSet`, set `maxValue = entry.getValue()`, and add `entry.getKey()`.
   * If `entry.getValue() == maxValue`, add `entry.getKey()` to `maxKeysSet`.
   *(Alternatively: skip the set and directly check `if (map.get(num % space) == maxCount)`).*

3. **Pass 3 - Find Global Minimum Seed:**
   * Initialize `minimum = Integer.MAX_VALUE`.
   * Iterate through `nums`.
   * If `maxKeysSet.contains(nums[i] % space)`:
     * `minimum = Math.min(minimum, nums[i])`.

4. **Return Result:**
   * Return `minimum`.

---

### Complexity Analysis
* **Time Complexity:** O(N) — A constant number of linear scans over `nums` and the unique remainder keys in the map.
* **Space Complexity:** O(N) — At most N distinct remainder entries stored in the `HashMap` and `HashSet`.