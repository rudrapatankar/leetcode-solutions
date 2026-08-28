import java.util.Queue;
class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> roots = new ArrayList<>();
        List<List<Integer>> adj = new ArrayList<>();
        if(n<=2)
        {
            for (int i = 0; i < n; i++) roots.add(i);
            return roots;
        }
        for (int i = 0; i < n; i++) {
    adj.add(new ArrayList<>());
}
        int degree[]=new int[n];
        int l=edges.length;
        for(int i=0;i<l;i++)
        {
            adj.get(edges[i][0]).add(edges[i][1]);
            adj.get(edges[i][1]).add(edges[i][0]);
            degree[edges[i][0]]++;
            degree[edges[i][1]]++;
        }
        for(int i=0;i<n;i++)
        {
            if(degree[i]==1)
            {
                roots.add(i);
            }
        }
        int remaining_nodes = n;
        while(remaining_nodes>2)
        {
           remaining_nodes-=roots.size();
           int num = roots.size();
           for(int i=0;i<num;i++)
           {
            int c = roots.remove(0);
            for(int neighbour: adj.get(c))
            {
                degree[neighbour]--;
                if(degree[neighbour]==1)
                {
                    roots.add(neighbour);
                    break;
                }
            }
           }
        }
        return roots;
    }
}