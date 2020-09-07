package com.question.pmp;

/**
 * Given the strings str1 and str2, write an efficient function deletionDistance that returns the deletion distance between them.
 * Explain how your function works, and analyze its time and space complexities
 * 
 * input:  str1 = "dog", str2 = "frog"
 * output: 3
 *
 * input:  str1 = "some", str2 = "some"
 * output: 0
 *
 * input:  str1 = "some", str2 = "thing"
 * output: 9
 *
 * input:  str1 = "", str2 = ""
 * output: 0
 * 
 */
public class DeleteDistance {
    /**
     * 
     * dp[i][j]: min num steps to make 1 and 2 same, prefix upto i [0, word1.length] of word1 and j [0, word2.length]
     * @param word1
     * @param word2
     * @return
     */
    static int deletionDistance(String word1, String word2) {

        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];

        //init, "sea" vs "", both ""
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    //curr chars equal, min step is same as not having both chars
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    //min step depends on smaller result so far + 1 (delete curr char), either not include curr char in word1 or not include curr char in word2
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + 1;
                }
            }
        }

        return dp[m][n];

    }


    public static void main(String[] args) {
        System.out.println(deletionDistance("dog", "frog"));
        System.out.println(deletionDistance2("dog", "frog"));
        System.out.println(deletionDistance3("dog", "frog"));
        System.out.println(deletionDistance4("dog", "frog"));
    }
    
    
    static int deletionDistance2(String str1, String str2) {
        //min num of chars to delete, min num of difference chars, 0 if all same, len1 + len2 if no same chars

        if (str1 == null || str2 == null) return -1;

        int m = str1.length();
        int n = str2.length();
        //longest common subsequence of two strs, use lcs len, answer is len1 + len2 - 2 * lcs len
        return m + n - 2 * lcs(str1, str2, m, n);
    }

    static int lcs(String s1, String s2, int i, int j) {
        //ret the len of lcs among s1 and s2, with their lengths upto i and j
        //base case, one of str is empty
        if (i == 0 || j == 0) return 0;

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            return 1 + lcs(s1, s2, i - 1, j - 1);
        } else {
            return Math.max(lcs(s1, s2, i, j - 1), lcs(s1, s2, i - 1, j));
        }

        //tc: O(2 ^(max(m, n))) recurstion depth max(m, n), at each recursion branch factor is 2, loose upper bound; sc: max(m, n) 
    }


    //improved recurision, store intermediate result in memo array
    static int deletionDistance3(String str1, String str2) {
        if (str1 == null || str2 == null) return -1;

        int m = str1.length(), n = str2.length();
        int[][] memo = new int[m + 1][n + 1];//consider "", size + 1

        return m + n - 2 * lcs(str1, str2, m, n, memo);

    }

    static int lcs(String s1, String s2, int i, int j, int[][] memo) {
        if (i == 0 || j == 0) return 0;
        //if curr memo exists, ret
        if (memo[i][j] != 0) return memo[i][j];

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            memo[i][j] = 1 + lcs(s1, s2, i - 1, j - 1, memo);
        } else {
            //if last pos char not equal, keep max of leave alone curr i or curr j - i - 1, j or i, j - 1
            memo[i][j] = Math.max(lcs(s1, s2, i - 1, j, memo), lcs(s1, s2, i, j - 1, memo));
        }

        return memo[i][j];
    }
    //tc: memo array each cell caculated once, O(mn); sc: size of memo array O(mn), recur stack depth is O(max(m,n))

    //DP, dp[i][j] means lcs length of prefix i in str1 and prefix j in str2
    static int deletionDistance4(String str1, String str2) {
        if (str1 == null || str2 == null) return -1;

        int m = str1.length(), n = str2.length();
        int[][] dp = new int[m + 1][n + 1];
        dp[0][0] = 0;//both ""

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    //remove either char from 1 or 2, keep the larger value as current sub result
                    dp[i][j] = Math.max(dp[i - 1][j],  dp[i][j - 1]);
                }
            }
        }

        return m + n - 2 * dp[m][n];
    //TC: O(mn)  SC:O(mn)
    }

}
