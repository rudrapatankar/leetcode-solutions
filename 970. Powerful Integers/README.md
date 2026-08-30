# Powerful Integers - Bounded Generation vs. Sequential Search

## 1. Problem Overview
Given three integers `x`, `y`, and `bound`, return a list of all powerful integers.
A powerful integer is an integer that can be represented as:

> value = x^i + y^j, where i >= 0, j >= 0, and value <= bound

The result should contain no duplicate values and can be returned in any order.

---

## 2. Initial Approach: Sequential Check with Greedy Subtraction

### The Initial Idea
Iterate through every integer `n` from the minimum possible sum (`x^0 + y^0 = 2`) up to `bound`. For each `n`, attempt to greedily subtract the largest power of `x`, and then check if the remainder is an exact power of `y`. If the remainder reduces to 0, mark `n` as valid.

### Why This Approach Failed

1. **Greedy Subtraction Flaw:**
   * A valid sum might require a smaller power of `x` so that the remaining difference matches a power of `y`.
   * *Example:* For `n = 17, x = 2, y = 5`. The valid representation is `2^4 + 5^0 = 16 + 1 = 17`. A greedy strategy that picks the largest available power might select sub-optimal intermediate values and fail to find the exact match.
2. **Infinite Loops on Base 1:**
   * When `x = 1` or `y = 1`, `1^k = 1` for all values of `k`. An unbounded loop looking for strictly larger powers never increments beyond 1, causing a Time Limit Exceeded (TLE) error.
3. **Massive Search Space Inefficiency:**
   * Testing all numbers from 2 up to `bound = 1,000,000` forces `1,000,000` individual checks, even though the total count of distinct powerful integers is relatively tiny.

---

## 3. The Optimal Approach: Direct Power Generation (Bounded Search Space)

### Core Intuition
Instead of testing whether every integer up to `bound` is powerful, directly generate the set of valid combinations:
> *"What values of x^i + y^j can be formed such that x^i < bound and y^j < bound?"*

Because powers grow exponentially, the number of powers of `x` and `y` strictly below `bound` is bounded logarithmically by `O(log_x(bound))` and `O(log_y(bound))`.

### Why Generation is Better
* **Eliminates Unnecessary Checks:** Skips non-powerful numbers entirely instead of scanning 1,000,000 values sequentially.
* **Direct Calculation:** Evaluates valid combinations directly using simple exponent iteration.
* **O(1) Duplicate Elimination:** Uses a `HashSet` to store valid sums without duplicate values.

---

## 4. Step-by-Step Generation Logic

1. **Find Maximum Exponents:**
   * Increment `max_pow_x` while `x^max_pow_x < bound`. If `x == 1`, limit `max_pow_x = 1`.
   * Increment `max_pow_y` while `y^max_pow_y < bound`. If `y == 1`, limit `max_pow_y = 1`.
2. **Generate Sum Combinations:**
   * Loop index `i` from `0` to `max_pow_x - 1`.
   * Loop index `j` from `0` to `max_pow_y - 1`.
   * Calculate `num = x^i + y^j`.
   * If `num <= bound`, add `num` to the `HashSet`.
3. **Final Answer:**
   * Convert the `HashSet` to an `ArrayList` and return it.
