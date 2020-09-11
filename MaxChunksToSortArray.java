package com.question.ms;

/**
 * Given array A with N distinct integer, sort array into ascending order
 * first, divide it into 1 or more slices, sort each slice, then join sorted slice in same order
 * return max number of slices
 * 
 * A =[2,4,1,6,5,9,7] 
 * split into [2,4,1] [6,5] [9,7] -> sort slice [1,2,4] [5,6] [7,9]
 * return 3
 */
public class MaxChunksToSortArray {
    //2,4,1,6,5,9,7
    //1,2,4,5,6,7,9
    
    static int solve(int[] nums) {
        //iterate nums, at any point if all num to the current left <= all num to the right, there is a new chunk
        //two [] for storing max left, min right
        int n = nums.length;
        int[] maxOfLeft = new int[n];
        maxOfLeft[0] = nums[0];
        int[] minOfRight = new int[n];
        minOfRight[n - 1] = nums[n - 1];
        
        for (int i = 1; i < n; i++) {
            maxOfLeft[i] = Math.max(nums[i], maxOfLeft[i - 1]);//2,4,4,6,6,9,9
        }
        
        for (int i = n - 2; i >= 0; i--) {
            minOfRight[i] = Math.min(nums[i], minOfRight[i + 1]);//1,1,1,5,5,7,7
        }
        
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (i == n - 1 || maxOfLeft[i] <= minOfRight[i + 1]) {
                cnt++;
            }
        }
        return cnt;
    }
    
    public static void main(String[] args) {
        int[] nums1 = {2,4,1,6,5,9,7};
        int[] nums2 = {4,3,2,6,1};
        int[] nums3 = {2,1,6,4,3,7};
        int[] nums4 = {2147483643, 2147483646,3,4};
        System.out.println(solve(nums1));
        System.out.println(solve(nums2));
        System.out.println(solve(nums3));
        System.out.println(solve(nums4));
    }
}
