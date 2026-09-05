# Searching a Rotated Sorted Array via Minimum Index Partitioning

## Conceptual Overview
A rotated sorted array consists of two separate, internally sorted segments placed side-by-side. By first isolating the rotation pivot—which corresponds to the index of the minimum element—the array can be split cleanly into two distinct, monotonically increasing subranges:
* **Left Subarray:** From index `0` up to `min_index - 1`
* **Right Subarray:** From index `min_index` up to `nums.length - 1`

Once the pivot is found using a modified binary search, the target's value is compared against the boundary values (`nums[min_index]` and the final element). This $\mathcal{O}(1)$ range check determines which of the two sorted halves the target must inhabit, routing the search to the correct subset without scanning elements linearly.

## Relation to Binary Search
This approach relies entirely on the mechanics of binary search, deploying it across two sequential phases:

1. **Pivot Localization:** The first binary search phase finds the minimum element by exploiting gradient changes. If `nums[mid] > nums[ub]`, the pivot must reside in the right half, so `lb` moves to `mid + 1`. Otherwise, the pivot is at or to the left of `mid`, allowing `ub` to shrink to `mid`.
2. **Target Search:** The second phase applies standard binary search on whichever localized subsegment matches the target's value range. Because both subsegments are strictly sorted in ascending order, traditional midpoint comparisons (`nums[mid] == target`, `<`, or `>`) function properly.

## Complexity Analysis

* **Time Complexity:** $\mathcal{O}(\log n)$
  The algorithm executes two sequential binary searches. Finding the minimum index takes $\mathcal{O}(\log n)$ steps, and searching the target within the chosen subsegment takes another $\mathcal{O}(\log n)$ steps. Mathematically, $\mathcal{O}(\log n) + \mathcal{O}(\log n)$ simplifies to $\mathcal{O}(\log n)$.
* **Space Complexity:** $\mathcal{O}(1)$
  No auxiliary data structures, recursion stacks, or copies of the array are created. The search operates entirely in place using a few pointer variables (`lb`, `ub`, `mid`), consuming constant memory.