import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public boolean find132pattern(int[] nums) {
        Deque<Integer> stack = new ArrayDeque<>();
        int maxMed = Integer.MIN_VALUE;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (!stack.isEmpty()) {
                while (!stack.isEmpty() && stack.peek() < nums[i]) {
                    maxMed = Math.max(maxMed, stack.pop());
                }
                if (nums[i] < maxMed) {
                    return true;
                }
            }
            stack.push(nums[i]);
        }
        return false;
    }
}