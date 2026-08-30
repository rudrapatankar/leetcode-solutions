class Solution {
    public int destroyTargets(int[] nums, int space) {
        HashMap<Integer, Integer> target = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int count = target.getOrDefault(nums[i] % space, 0);
            target.put(nums[i] % space, count + 1);
        }
        Set<Integer> maxKeysSet = new HashSet<>();
        int maxValue = Integer.MIN_VALUE;
        int minimum = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Integer> entry : target.entrySet()) {
            int value = entry.getValue();
            if (value > maxValue) {
                maxValue = value;
                maxKeysSet.clear();
                maxKeysSet.add(entry.getKey());
            } else if (value == maxValue) {
                maxKeysSet.add(entry.getKey());
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (maxKeysSet.contains(nums[i] % space)) {
                if (nums[i] < minimum) {
                    minimum = nums[i];
                }
            }
        }
        return minimum;
    }
}