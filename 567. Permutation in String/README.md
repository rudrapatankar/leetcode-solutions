# Permutation in String (Sliding Window & Frequency Arrays)

> **Learning Context & Disclaimer:** 
> For the correct execution of this sliding window approach—specifically the transition away from HashMaps and the `current_index - window_length` mathematical formula I needed to search up a lot or use AI. Update the readme once implemented after 20 days from now

## The Optimized Approach: Sliding Window with `int[26]`
This solution uses a fixed-size sliding window to find if any permutation of `s1` exists within `s2`. Instead of checking every possible substring from scratch, it maintains a running tally of character frequencies.

1. **Initial Setup:** Create two integer arrays of size 26. Populate the first array with the character frequencies of `s1`. Populate the second array with the character frequencies of the *first window* of `s2` (from index `0` to `s1.length() - 1`).
2. **The Slide:** Iterate through `s2` starting from the end of the first window. For each step:
   * Compare the two arrays. If they match, a permutation is found.
   * **Add:** Increment the count for the new character entering the front of the window (`s2.charAt(i)`).
   * **Remove:** Decrement the count for the old character falling out the back of the window (`s2.charAt(i - s1.length())`).
3. **Final Check:** A final comparison is required outside the loop to ensure the very last window is evaluated.

## Why this is Better (Flaws of the Initial HashMap Approach)

The initial attempt relied on tracking the entirety of `s2` in a HashMap and generating substrings inside a `while` loop. That approach contained several fatal flaws that this array-based sliding window fixes:

### 1. Global vs. Local Frequencies
* **The Flaw:** The original code mapped the frequencies of the *entire* `s2` string upfront. When comparing a small substring window to the global HashMap, the counts would never match (e.g., comparing the count of 'a' in a 2-letter window against the count of 'a' in a 10,000-letter string).
* **The Fix:** The sliding window array strictly tracks only the characters currently inside the frame. 

### 2. The Substring Performance Trap
* **The Flaw:** Generating `s2.substring(start, end)` inside a loop and iterating over it character-by-character resulted in a time complexity of O(N × M). This completely negates the purpose of a sliding window.
* **The Fix:** By mathematically targeting only the two edges (the character entering and the character leaving), the inner loop is eliminated. The frequency array updates in two constant-time operations, dropping the time complexity to a highly optimal O(N).

### 3. HashMap Overhead vs. Array Efficiency
* **The Flaw:** HashMaps in Java require wrapper classes (`Character`, `Integer`), which causes constant boxing and unboxing memory overhead. Furthermore, searching for a character not present in the map risks a `NullPointerException` unless handled carefully with `getOrDefault()`.
* **The Fix:** Because the problem is constrained to 26 lowercase English letters, an `int[26]` array provides true O(1) performance. Accessing an index via `char - 'a'` is a direct memory jump with zero hashing overhead, and all default values are safely initialized to `0`. Comparing two arrays is also a heavily optimized, constant-time operation (`Arrays.equals()`).

## Time and Space Complexity
* **Time Complexity:** O(N), where N is the length of `s2`. The string is traversed exactly once, and updating/comparing the size-26 arrays takes O(1) constant time.
* **Space Complexity:** O(1). The algorithm only creates two `int[26]` arrays, which use a strict, constant amount of memory regardless of the input string sizes.