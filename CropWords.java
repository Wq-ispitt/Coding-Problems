package com.question.ms;

/**
 * K characters limit, crop the message too long.
 * String message, contains alphabet letters and spaces.
 * valid result: 1. not crop away part of word; 2.not end with space; 3.not exceed K limit; 4. as long as possible
 * 
 * message = "Codility We test coders", K = 14
 * return "Codility We"
 */
public class CropWords {

    static String cropWords(String s, int k) {
        int i = 0;
        int n = s.length();
        while (i < n && s.charAt(i) == ' ') i++;//leading space
        while (i < n) {
            int j = i;
            while (j < n && Character.isLetter(s.charAt(j))) j++;//j, ends at current word last char
            if (j <= k) {
                i = j;
                while (i < n && s.charAt(i) == ' ') i++;//find next word start
            } else {
                i--;
                break;
            }
        }
        if (i >= n) i--;
        while (i >= 0 && s.charAt(i) == ' ') i--;//no end with space
        
        return s.substring(0, i + 1);
    }
    
    public static void main(String[] args) {
        System.out.println(cropWords("Codility We test coders", 14));
        System.out.println(cropWords("      Codility We test coders     ", 14));
    }
}
