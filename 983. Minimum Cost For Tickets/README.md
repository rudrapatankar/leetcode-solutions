# Minimum Cost For Tickets - 1D Dynamic Programming Approach

## 1. Problem Overview
You are given an array of unique integers `days` representing the days of the year you will travel, and an array `costs` of size 3 representing the cost of:
* A 1-day pass (`costs[0]`)
* A 7-day pass (`costs[1]`)
* A 30-day pass (`costs[2]`)

The goal is to find the minimum cost needed to cover every travel day in the `days` array.

---

## 2. Comparing with the Coin Change DP Approach

### Why the "Coin Change" Intuition Made Sense
In the classic **Coin Change Problem**, to find the minimum cost to make an amount $A$, you look backwards at previous states by subtracting coin denominations:
$$\text{dp}[A] = \min_{c \in \text{coins}}(\text{dp}[A - c] + 1)$$

Your initial thought to model ticket passes as "coins" that subtract duration ($1, 7, 30$ days) was mathematically on the right track:
* Passes act like coin denominations of duration $1, 7, 30$.
* Each pass has an associated cost (weight).
* The goal is to minimize the total accumulated cost up to the target day.

---

### Why Standard Coin Change Failed (Problem-Specific Constraints)

While the core dynamic programming concept was solid, applying Coin Change directly broke down due to two domain-specific rules of calendar travel:

| Dimension | Classic Coin Change | Minimum Cost For Tickets | Why Coin Change Fails Directly |
| :--- | :--- | :--- | :--- |
| **Intermediate Values** | Every value from $0 \dots A$ represents an exact sum to build. | Only specific days require travel; non-travel days are "free". | If non-travel days are skipped, they remain uninitialized (cost = 0), corrupting future lookbacks. |
| **Boundaries** | You cannot use a coin larger than the remaining amount ($A \ge c$). | A 7-day or 30-day pass bought on day 2 covers earlier days for free. | A condition like `if (day >= pass)` ignores buying multi-day passes on early travel days. |
| **Cost Propagation** | Value resets per denomination. | A non-travel day simply inherits the cost from the previous day (`minCost[i] = minCost[i-1]`). | Cost must continuously flow forward through calendar rest days. |

---

## 3. Required Modifications to Adapt Coin Change

To transform the Coin Change approach to work for ticket scheduling:

1. **Iterate All Calendar Days (1 to `lastDay`):**
   Instead of looping only over the elements in `days`, loop through every single integer day $1 \le i \le \text{lastDay}$.
2. **Propagate Non-Travel Days:**
   If day $i$ is not a travel day, no new pass is required:
   $$\text{minCost}[i] = \text{minCost}[i - 1]$$
3. **Clamp Negative Lookbacks with `Math.max(0, i - pass[j])`:**
   Instead of forbidding passes when $i < \text{pass}[j]$, allow the pass to reach all the way back to Day 0 (base cost `0`).
4. **Take the Minimum Over All 3 Passes:**
   $$\text{minCost}[i] = \min \begin{cases}
   \text{minCost}[\max(0, i - 1)] + \text{costs}[0] \\
   \text{minCost}[\max(0, i - 7)] + \text{costs}[1] \\
   \text{minCost}[\max(0, i - 30)] + \text{costs}[2]
   \end{cases}$$
