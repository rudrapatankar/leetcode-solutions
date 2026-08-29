class Solution {
    public int mincostTickets(int[] days, int[] costs) {
        int minCost[] = new int[days[days.length - 1] + 1];
        boolean travel[] = new boolean[days[days.length - 1] + 1];
        for (int i = 0; i < days.length; i++) {
            travel[days[i]] = true;
        }
        int pass[] = { 1, 7, 30 };
        minCost[0] = 0;
        for (int i = 1; i <= days[days.length - 1]; i++) {
            minCost[i] = Integer.MAX_VALUE;
            if (!travel[i]) {
                minCost[i] = minCost[i - 1];
                continue;
            }
            for (int j = 0; j < pass.length; j++) {
                int lookBackDay = Math.max(0, i - pass[j]);
                int cost = minCost[lookBackDay] + costs[j];
                if (cost < minCost[i]) {
                    minCost[i] = cost;
                }
            }
        }
        return minCost[days[days.length - 1]];
    }
}