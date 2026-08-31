# Remove Duplicates from Sorted List II - Sentinel & Atomic Lookahead

## 1. Problem Overview
Given the `head` of a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list. Return the linked list sorted as well.

Unlike standard duplicate removal where one representative copy is kept, this problem requires completely eliminating every node whose value appears more than once.

---

## 2. Initial Pitfalls: Flag-Based Deletion
Attempting to use a tracking variable (`int deleted`) and two step-by-step pointers (`temp1`, `temp2`) to skip duplicates fails due to several core issues:

* **Memory Desynchronization:** Rewiring `temp1.next` modifies the active list, but `temp2` continues along the old memory connections. This easily creates self-referencing infinite loops (e.g., `node.next = node`).
* **Anchor Loss:** Advancing a pointer immediately after a deletion moves it onto an unverified replacement node. If that replacement is also a duplicate, the anchor needed to rewire around it is permanently lost.
* **Hardcoded Skips:** Skipping exactly two nodes (`temp1.next = temp2.next.next`) fails on duplicate clusters of size 3 or more (e.g., `2 -> 2 -> 2`), leaving residual duplicates in the final list.
* **Head Overwrites:** Reassigning `head` without a dummy node risks dropping previously validated unique nodes or throwing `NullPointerException`s on empty tails.

---

## 3. Optimal Strategy: Sentinel Node + Atomic Lookahead
Instead of reacting to duplicates after the fact, process the list by fast-forwarding through entire duplicate clusters at once using a **Sentinel (Dummy) Node**.

### The Core Logic (`prev.next != curr`)
Because the list is sorted, duplicates are strictly adjacent. You can validate duplicates purely through pointer movement:
1. **Initialize:** `curr = prev.next`.
2. **Lookahead:** Fast-forward `curr` to the end of any matching values (`while (curr.val == curr.next.val)`).
3. **Branch Decision:**
   * **If `prev.next != curr` (Duplicates Found):** `curr` moved. Bypass the entire cluster by setting `prev.next = curr.next`. **Do not advance `prev`**, as the new node must be checked.
   * **If `prev.next == curr` (Node is Unique):** `curr` never moved. Safely advance the anchor by setting `prev = prev.next`.

### Complexity Analysis
* **Time Complexity:** $O(n)$ — Every node is visited at most twice (once by `curr` during lookahead and once by `prev`), guaranteeing a strict linear pass.
* **Space Complexity:** $O(1)$ — Only pointer references are maintained; list modifications are performed completely in place.