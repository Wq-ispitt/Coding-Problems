package com.question.pmp;

import java.util.HashSet;
import java.util.Set;

public class GetDiffNumber {

    // arr = [0, 1, 2, 3] ret 4
    // len 4, arr = [1,2,3,4] ret 0 [0, len of arr]
/*

[1, 3, 0, 2] ret 4

[0, 1, 3, 2, 6, 8, 7, 9, 5, 4]

*/
    //sort arr first, nlogn -> n Time O(n)
    //set, 1. iterate arr, if Space O(n)
    //Set<Integer> set = new HashSet<>();
    //find a solution with O(n)Time O(1)Space - in place solution
    static int missingNumberInPlace(int[] arr) {
        //push every number to its corresponding index in the array
        //input arr modified
        int n = arr.length;
        int target;
        for (int i = 0; i < n; i++) {
            target = arr[i];
            while (target < n && arr[target] != target) {
                //original number in the target index is kicked out
                int tmp = arr[target];
                arr[target] = target;
                target = tmp;
            }
        }

        //iterate arr again, this time find i != arr[i] position, return or len of arr
        for (int i = 0; i < n; i++) {
            if (i != arr[i]) return i;
        }

        return n;
    }
    
    static int missingNumberUsingSum(int[] nums) {
        int actualSum = 0;
        int expectedSum = 0;
        int i;
        for (i = 0; i < nums.length; i++) {
            actualSum += nums[i];
            expectedSum += i;
        }

        return expectedSum + i - actualSum;
    }

    public static int missingNumberSet(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int range = nums.length + 1;
        for (int num = 0; num < range; num++) {
            if (!set.contains(num)) {
                return num;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {0, 1, 2, 3};
        int[] arr2 = {1, 0, 2, 3};
        int[] arr3 = {0, 5, 2, 4, 1, 3};
        int[] arr4 = {9,6,4,2,3,5,7,0,1};
//        System.out.println(missingNumberInPlace(arr));
//        System.out.println(missingNumberInPlace(arr2));
        System.out.println(missingNumberInPlace(arr3));
        System.out.println(missingNumberInPlace(arr4));
//        System.out.println(findMissingNumber(arr));
//        System.out.println(findMissingNumber(arr2));
        System.out.println(missingNumberUsingSum(arr3));
        System.out.println(missingNumberSet(arr3));
        
    }

    
}
