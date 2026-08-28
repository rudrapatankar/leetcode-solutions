class Solution {
    private static void explore(int [][] adj, int x, int y, List<Integer> path,List<List<Integer>>MasterPath) {
        // int n = adj.length;
        if (x == y) {
            MasterPath.add(new ArrayList<>(path));
            return;
        }
        for (int j = 0; j < adj[x].length; j++) {
                
                path.add(adj[x][j]);
               explore(adj, adj[x][j], y,path,MasterPath);
               path.remove(path.size()-1);
            }
        }
    
    
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) 
    {
        int n = graph.length;
        List<List<Integer>> MasterPath = new ArrayList<>();
        
            List<Integer> path = new ArrayList<>();
            path.add(0);
            explore(graph,0,n-1,path,MasterPath);
        return MasterPath;
    }
}