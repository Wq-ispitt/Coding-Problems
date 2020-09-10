package com.question.amazon;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given a list of positive integers nums and an int target, return indices of the two numbers such that they add up to a target - 30.
 *
 * Conditions:
 *
 * You will pick exactly 2 numbers.
 * You cannot pick the same element twice.
 * If you have multiple pairs, select the pair with the largest number
 * 
 * Example 1:
 *
 * Input: nums = [1, 10, 25, 35, 60], target = 90
 * Output: [2, 3]
 * Explanation:
 * nums[2] + nums[3] = 25 + 35 = 60 = 90 - 30
 * Example 2:
 *
 * Input: nums = [20, 50, 40, 25, 30, 10], target = 90
 * Output: [1, 5]
 * Explanation:
 * nums[0] + nums[2] = 20 + 40 = 60 = 90 - 30
 * nums[1] + nums[5] = 50 + 10 = 60 = 90 - 30
 * You should return the pair with the largest number.
 * 
 */
public class FindPairwithGivenSum {

    static int[] Find2Sum(int[] nums, int target) {
        int[] res = new int[] {-1, -1};
        if (nums == null || nums.length == 0) return res;
        if (target < 30) return res;
        
        int tofind = target - 30;
        //map nums[i]=index in nums, so we can iterate nums and keep record of index, if map contains the difference we can add to res
        //difference can be nums[i], put target - nums[i] before
        int max = Integer.MIN_VALUE;
        Map<Integer, Integer> deltaNumToIndex = new HashMap<>();//<current delta against nums[i], current idx i in num>
        for (int i = 0; i < nums.length; i++) {
            if (deltaNumToIndex.containsKey(nums[i])) {
                //pair with largest number, either one of pair with idx number is larger, update max
                int firstIdx = deltaNumToIndex.get(nums[i]);
                if (nums[i] > max || nums[firstIdx] > max) {
                    res[0] = deltaNumToIndex.get(nums[i]);
                    res[1] = i;
                    max = Math.max(nums[i], nums[firstIdx]);//max of either two values
                }
            } 
            
            deltaNumToIndex.put(tofind - nums[i], i);
        }
        
        return res;
    }
    
    public static void main(String[] args) {
        int[] nums1 = new int[] {1, 10, 25, 35, 60};
        int t1 = 90;
        
        int[] nums2 = new int[] {20, 50, 40, 25, 30, 10};
        int t2 = 90;

        int[] nums3 = {50, 20, 10, 40, 25, 30};//0, 2 ret vs 1, 3
        int target3 = 90;

        int[] nums4 = {1, 2};
        int target4 = 90;
        
        System.out.println(Arrays.toString(Find2Sum(nums1, t1)));
        System.out.println();
        System.out.println(Arrays.toString(Find2Sum(nums2, t2)));
        System.out.println();
        System.out.println(Arrays.toString(Find2Sum(new int[]{0, 0}, 30)));
        System.out.println();
        System.out.println(Arrays.toString(Find2Sum(nums3, target3)));
        System.out.println();
        System.out.println(Arrays.toString(Find2Sum(nums4, target4)));
    }
}
