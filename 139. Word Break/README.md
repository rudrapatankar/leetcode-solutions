# Word Break - Dynamic Programming Approach

## 1. Problem Overview
Given a string `s` and a dictionary of strings `wordDict`, return `true` if `s` can be segmented into a space-separated sequence of one or more dictionary words. Words in `wordDict` can be reused an unlimited number of times.

---

## 2. Initial Approach: Greedy String Replacement (`s.replace()`)

### The Initial Idea
Loop through each word in `wordDict`. If `s` contains the word, delete it from `s` using `s.replace(word, "")` and increment a counter. Return `true` if `count > 0`.

### Why This Approach Failed

1. **Destroys Substring Adjacency (Creates False Merges):**
   * Removing a word from the middle of a string snaps the disconnected ends together, forming words that never existed sequentially in the original string.
   * *Example:* For `s = "catsanddog"`, removing `"sand"` collapses the string into `"catdog"`.
2. **Order Dependency & Greedy Traps:**
   * A single pass over `wordDict` depends entirely on the order of words in the dictionary.
   * *Example:* If `s = "cars"` and `wordDict = ["car", "ca", "rs"]`, matching and removing `"car"` first leaves `"s"`, which fails. The valid split `"ca"` + `"rs"` is never explored.
3. **Invalid Success Condition (`count > 0`):**
   * Matching only one word (e.g., `"cat"` in `"catsandog"`) increments `count`, falsely returning `true` even though the remaining suffix (`"sandog"`) cannot be segmented.

---

## 3. The Optimal Approach: 1D Dynamic Programming

### Core Intuition
Instead of mutating the string, we test valid prefixes incrementally:
> *"If the prefix $s[0 \dots j-1]$ can be segmented into valid words, and the substring $s[j \dots i-1]$ is in the dictionary, then the prefix $s[0 \dots i-1]$ is also valid."*

### Why DP is Better
* **Preserves Original Order:** Substring boundaries are checked in place using indices without altering the original string.
* **Explores All Split Combinations:** Systematically tests every valid partition point $j$ rather than making irreversible greedy choices.
* **$O(1)$ Word Lookups:** Using a `HashSet` allows instantaneous verification of dictionary substrings.

---

## 4. Step-by-Step DP Logic

1. **State Definition:** 
   * `dp[i]` is a boolean representing whether the prefix of length `i` (`s[0...i-1]`) can be successfully segmented.
2. **Base Case:** 
   * `dp[0] = true` (an empty prefix of length 0 is always valid).
3. **Transition:** 
   * For every index $i$ from $1$ to $n$:
     * Check every previous split point $j$ ($0 \le j < i$).
     * If `dp[j] == true` AND `Dict.contains(s.substring(j, i)) == true`:
       * Set `dp[i] = true`.
4. **Final Answer:** 
   * Return `dp[s.length()]`.
