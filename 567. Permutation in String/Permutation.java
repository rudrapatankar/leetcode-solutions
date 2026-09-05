class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        int freqCount1[] = new int[26];
        int freqCount2[] = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            freqCount1[s1.charAt(i) - 'a']++;
            freqCount2[s2.charAt(i) - 'a']++;
        }
        for (int i = s1.length(); i < s2.length(); i++) {
            if (Arrays.equals(freqCount1, freqCount2)) {
                return true;
            }
            freqCount2[s2.charAt(i) - 'a']++;
            freqCount2[s2.charAt(i - s1.length()) - 'a']--;
        }
        return Arrays.equals(freqCount1, freqCount2);
    }
}