# Integer to Roman 

## Problem Overview
The goal is to convert an integer to its Roman numeral representation. Roman numerals are typically written largest to smallest from left to right. However, there are six specific subtractive exceptions where a smaller numeral precedes a larger one (e.g., `4` is `IV`, not `IIII`).

---

## The Initial Approach (Mathematical Isolation)
The first attempt at solving this problem relied on mathematically isolating digits to dynamically detect the subtractive edge cases (`4` and `9`). 

The logic was built around:
1. Using `Math.log10()` and `Math.pow()` to extract the most significant digit of the current number.
2. Checking if this digit was `4` or `9`.
3. If true, passing the number to a helper function (`fourandnine`) to dynamically calculate the upper bound (e.g., finding `50` for `40`) and appending the corresponding character.
4. Otherwise, proceeding with standard greedy subtraction using a limited symbol array: `[1000, 500, 100, 50, 10, 5, 1]`.

### Critical Bugs in the Initial Approach
This mathematical approach was over-engineered and introduced several fatal flaws:

1. **The `Math.log10(0)` Crash:** 
   When the remaining `num` reached `0` (e.g., after subtracting `10` from `10`), the program attempted to execute `Math.log10(0)`. In Java, this evaluates to negative infinity, which causes a crash when cast to an integer and used in `Math.pow()`.
2. **Pass-by-Value State Desync:** 
   Java passes primitive data types (like `int`) by value. When the `fourandnine` method subtracted the upper bound from `num`, it did *not* update the `num` variable in the main `helper` loop. This resulted in infinite loops or duplicated processing, as the main loop was completely unaware that `num` had been modified.
3. **Incomplete String Generation:** 
   The dynamic subtraction logic was fundamentally flawed. For an input like `40`, it correctly identified `50` as the upper bound but only appended `"L"` to the string instead of `"XL"`. Dynamically constructing the correct two-character subtractive string required even more convoluted logic.

---

## The Refined Approach (Data Structure Over Logic)
The complex mathematics and state management issues were eliminated by changing the fundamental perspective of the problem: **Treating subtractive forms as first-class symbols.**

Instead of writing `if/else` logic to calculate when a `4` or `9` appears, the subtractive exceptions (`IV`, `IX`, `XL`, `XC`, `CD`, `CM`) are baked directly into the data structures alongside the standard numerals.

### The Refined Data Structures:
* **Expanded Array:** `[1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1]`
* **Expanded HashMap:** Explicitly mapping `900 -> "CM"`, `400 -> "CD"`, `90 -> "XC"`, `40 -> "XL"`, `9 -> "IX"`, and `4 -> "IV"`.

### How It Works (The Greedy Algorithm)
By keeping the symbol array strictly sorted in descending order, the problem is solved using a simple greedy algorithm:

1. Iterate through the `symbol` array from largest to smallest.
2. While the current `num` is greater than or equal to the current `symbol`, subtract the `symbol` from `num`.
3. Append the corresponding Roman numeral string from the `HashMap` to the result.
4. Repeat until `num` is `0`.

**Example Walkthrough for `num = 494`:**
* Checks `1000`, `900`, `500` -> all too large.
* Hits `400` -> `num >= 400`. Subtracts `400` (`num` becomes `94`), appends `"CD"`.
* Continues down the array...
* Hits `90` -> `num >= 90`. Subtracts `90` (`num` becomes `4`), appends `"XC"`.
* Continues down the array...
* Hits `4` -> `num >= 4`. Subtracts `4` (`num` becomes `0`), appends `"IV"`.
* **Final Output:** `"CDXCIV"`

### Complexity
* **Time Complexity:** $O(1)$ - The maximum possible value is 3999. The algorithm iterates over a fixed array of 13 elements, executing in constant time regardless of the input size.
* **Space Complexity:** $O(1)$ - The memory used for the `HashMap` and array is constant and does not scale with the input.