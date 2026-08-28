class Solution {
    public static void explore(int [][] adj, int x, int[] visited) {
        // int n = adj.length;
        
        visited[x] = 1;
        for (int j = 0; j < adj[x].length; j++) {
            if(adj[x][j]==1&&visited[j]==0)
            {
                explore(adj,j,visited);
            }
            
            }
        }


    
    public int findCircleNum(int[][] isConnected) 
    {
        int l = isConnected.length;
        int visited[]=new int[l];
        int provinceCount=0;
        for(int i=0;i<l;i++)
        {
            if(visited[i]==0){
            provinceCount++;
            explore(isConnected,i,visited);
            }
        }
        return provinceCount;
        
    }   
}