package com.question.pmp;

import java.util.*;

/**
 * 1. Given a string s containing just the characters ( ) { } [ ]
 * determine if the input string is valid.
 * open brackets must be closed by the same type of brackets; open brackets must be closed in the correct order
 * 
 * ex1: ()[(] false
 * 
 * 
 * 
 * 2. Given a non-empty array of integers, return the k most frequent elements
 * ex1: 
 * Input: nums = [1, 1, 1, 2, 2, 3] k = 2
 * Output: [1, 2]
 */
public class MockInterview1 {
    public static void main(String[] args) {
        MockInterview1 mockInterview1 = new MockInterview1();

        System.out.println("-----Question 1-----");
        String s1 = "()[(]";
        String s2 = "({[]})";
        System.out.println(mockInterview1.checkValid(s1));
        System.out.println(mockInterview1.checkValid(s2));
        
        int[] n1 = {1,1,2,2,3,3};
        int k1 = 2;
        int[] n2 = {1,1,2,3};
        int k2 = 5;
        int[] n3 = {1, 1, 1, 2, 2, 3};
        int k3 = 2;
        System.out.println("------Question 2-------");
        System.out.println(mockInterview1.mostKfrequent(n1, k1));
        System.out.println(mockInterview1.mostKfrequent(n2, k2));
        System.out.println(mockInterview1.mostKfrequent(n3, k3));
    }
    
    public boolean checkValid(String s) {
        if (s == null || s.isEmpty()) return false;
        
        int n = s.length();
        //use stack to match prev bracket and current bracket
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == '{' || s.charAt(i) == '[') {
                stack.push(s.charAt(i));
            } else {
                //current char is one of closing brackets, pop out its open bracket in stack, check stack size == 0 to see
                if (stack.isEmpty() || !isMatch(stack.peek(), s.charAt(i))) {
                    return false;
                }
                stack.pop();
            }
        }
        
        return stack.isEmpty();
    }
    
    private boolean isMatch(char prev, char curr) {
        return (prev == '(' && curr == ')') || (prev == '{' && curr == '}') || (prev == '[' && curr == ']');
    }
    
    public List<Integer> mostKfrequent(int[] nums, int k) {
        List<Integer> res = new LinkedList<>();
        if (nums == null) return res;

        //get frequency of each num
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num,0) + 1);
        }
        
        //use minheap sort by freq, keep size k, poll the smallest if larger than k
        PriorityQueue<Integer> minheap = new PriorityQueue<>((a, b) -> (map.get(a) - map.get(b)));
        for (int key : map.keySet()) {
            minheap.add(key);
            if (minheap.size() > k) {
                minheap.poll();
            }
        }
        //what if k > nums length
        int n = Math.min(k, minheap.size());
        for (int i = 0; i < n; i++) {
            res.add(0, minheap.poll());
        }
        
        return res;
    }
}
