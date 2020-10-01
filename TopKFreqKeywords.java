package com.question.amazon;

import java.util.*;

/**
 * Given a list of reviews, a list of keywords and an integer k. Find the most popular k keywords in order of most to least frequently mentioned.
 * The comparison of strings is case-insensitive.
 * Multiple occurrences of a keyword in a review should be considered as a single mention.
 * If keywords are mentioned an equal number of times in reviews, sort alphabetically.
 * 
 * Example 1:
 *
 * Input:
 * k = 2
 * keywords = ["anacell", "cetracular", "betacellular"]
 * reviews = [
 *   "Anacell provides the best services in the city",
 *   "betacellular has awesome services",
 *   "Best services provided by anacell, everyone should use anacell",
 * ]
 *
 * Output:
 * ["anacell", "betacellular"]
 *
 * Explanation:
 * "anacell" is occuring in 2 different reviews and "betacellular" is only occuring in 1 review.
 * Input:
 * 
 * k = 2
 * keywords = ["anacell", "betacellular", "cetracular", "deltacellular", "eurocell"]
 * reviews = [
 *   "I love anacell Best services; Best services provided by anacell",
 *   "betacellular has great services",
 *   "deltacellular provides much better services than betacellular",
 *   "cetracular is worse than anacell",
 *   "Betacellular is better than deltacellular.",
 * ]
 *
 * Output:
 * ["betacellular", "anacell"]
 *
 * Explanation:
 * "betacellular" is occuring in 3 different reviews. "anacell" and "deltacellular" are occuring in 2 reviews, but "anacell" is lexicographically smaller.
 * 
 */
public class TopKFreqKeywords {
    public static List<String> findTopK(int k, String[] keywords, String[] reviews) {
        List<String> res = new LinkedList<>();
        //use set, fast look up, note case insensitive, convert to one case
        Set<String> dict = new HashSet<>();
        for (String word : keywords) {
            dict.add(word.toLowerCase());
        }
        
        //use map to get word = frequency relationship
        Map<String, Integer> map = new HashMap<>();
        for (String review : reviews) {
            String[] str = review.toLowerCase().split("\\W");
            //System.out.println(Arrays.toString(str));
            //in dict but not in just added, a single mention
            Set<String> set = new HashSet<>();
            for (String s : str) {
                if (dict.contains(s) && !set.contains(s)) {
                    set.add(s);
                    map.put(s, map.getOrDefault(s, 0) + 1);
                }
            }
        }
        
        //keep a min heap of size k, reverse poll order is the top k frequent, O(nlogk)
        //sort on freq first, if equal on string alpha order
        Queue<Map.Entry<String, Integer>> minheap = new PriorityQueue<>(k,
                (e1, e2) -> e1.getValue().equals(e2.getValue()) ? e2.getKey().compareTo(e1.getKey()) : e1.getValue() - e2.getValue());
        
        Queue<String> pq = new PriorityQueue<>((o1, o2) -> {
            if (map.get(o1).equals(map.get(o2))) {
                return o2.compareTo(o1);
            } else {
                return map.get(o1) - map.get(o2);
            }
        });
        
        for (String word : map.keySet()) {
            pq.offer(word);
            if (pq.size() > k) {
                pq.poll();
            }
        }
        
        while (!pq.isEmpty()) {
            res.add(0, pq.poll());
        }
        
//        for (Map.Entry<String, Integer> entry : map.entrySet()) {
//            minheap.add(entry);
//            if (minheap.size() > k) minheap.remove();
//        }
//        
//        while (!minheap.isEmpty()) {
//            res.add(0, minheap.remove().getKey());
//        }
        
        return res;
    }

    public static void main(String[] args) {
        int k1 = 2;
        String[] keywords1 = { "anacell", "cetracular", "betacellular" };
        String[] reviews1 = { "Anacell provides the best services in the city", "betacellular has awesome services",
                "Best services provided by anacell, everyone should use anacell", };
        int k2 = 2;
        String[] keywords2 = { "anacell", "betacellular", "cetracular", "deltacellular", "eurocell" };
        String[] reviews2 = { "I love anacell Best services; Best services provided by anacell",
                "betacellular has great services", "deltacellular provides much better services than betacellular",
                "cetracular is worse than anacell", "Betacellular is better than deltacellular.", };

        System.out.println(findTopK(k1,keywords1,reviews1));
        System.out.println(findTopK(k2,keywords2,reviews2));
    }
}
