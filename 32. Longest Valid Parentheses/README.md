# Longest Valid Parentheses - Object-Oriented Stack Approach

## 1. Problem Overview
The objective is to find the length of the longest valid (well-formed) contiguous substring of parentheses. 

While a standard approach uses a stack of integers (indices), this solution leverages a custom `Bracket` class to maintain object-oriented principles, storing both the bracket character (`type`) and its index (`position`) in a `Stack<Bracket>`.

---

## 2. Core Approach & Logic

To measure contiguous blocks of valid parentheses (e.g., `()()()` = 6), it is not enough to just find matching pairs. We must establish **boundaries** and measure distances between them.

### Key Variables
* **`boundary` (The Hard Wall):** Initialized to `-1`. It represents the index of the last unmatched closing bracket. A valid sequence can never cross this wall.
* **`opening_brackets_stack`:** Stores unmatched opening brackets. The brackets sitting on the stack act as **temporary walls** for nested sequences.
* **`count` & `maxCount`:** `count` calculates the length of the currently resolved valid block, and `maxCount` stores the highest `count` seen so far.

### The Algorithm
1. **Pushing:** If an opening bracket is encountered, a new `Bracket` object is pushed onto the stack.
2. **Handling Invalid Closures:** If a closing bracket is encountered but the stack is empty, this bracket is invalid. We update `boundary = position` to mark a new "hard wall".
3. **Popping and Matching:** If the stack is not empty, we immediately `pop()` the top `Bracket`.
4. **Length Calculation (The Crucial Step):**
   Once a matching pair is popped, how far back does our valid sequence stretch?
   * **Case A: The stack is completely empty after popping.**
     This means every opening bracket encountered since the last hard wall has been perfectly matched. The sequence stretches all the way back to the `boundary`.
     *Formula:* `count = position - boundary;`
   * **Case B: The stack is NOT empty after popping.**
     This means there are still unmatched opening brackets (e.g., the first `(` in `(()`). The valid sequence only stretches back to the bracket that is *currently sitting on top of the stack* (the temporary wall).
     *Formula:* `count = position - opening_brackets_stack.peek().position;`

---

## 3. Bug History & Refinements

During the development of this algorithm, several logical traps were encountered. Here is a breakdown of the bugs and how they were resolved:

| Bug Encountered | Flawed Logic | How it was Fixed |
| :--- | :--- | :--- |
| **Peeking without Popping** | Using `stack.peek()` to check for matches but failing to `pop()` the matched bracket off the stack. | Added `stack.pop()` upon a successful match so that inner nested brackets are cleared, exposing the outer brackets. |
| **Ignoring the Boundary** | Using `if (stack.empty()) { continue; }` when encountering an unmatched closing bracket. | Introduced the `boundary` variable. An unmatched closing bracket breaks the sequence permanently, so it must be recorded as the new `boundary`. |
| **Measuring Isolated Pairs** | Calculating `count = position - ob.position + 1`. This only measured the length of the immediate pair (usually 2), failing to stitch adjacent pairs like `()()` together. | Shifted the calculation to measure the distance back to the `boundary` or the *previous* element on the stack, naturally grouping adjacent pairs. |
| **The "Left Behind" Trap** | In the `else` block, calculating `count = position - ob.position`. On a string like `"(()"`, this measured `2 - 1 = 1`, resulting in odd lengths. | Changed the math to `position - stack.peek().position`. Instead of measuring from the popped bracket, we must measure from the bracket *left behind* on the stack to properly capture nested sequences. |