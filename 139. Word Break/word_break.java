import java.util.HashSet;

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        HashSet<String> Dict = new HashSet<>(wordDict);
        int length = wordDict.size();
        int count = 0;
        String sub = "";
        if (length == 0 || s.length() == 0) {
            return false;
        }
        boolean dp[] = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j <= i - 1; j++) {
                if (dp[j]) {
                    sub = s.substring(j, i);
                    dp[i] = (Dict.contains(sub)) ? true : dp[i];
                }
            }
        }
        return dp[s.length()];
    }
}