package test;

import java.util.Arrays;

public class CountWordinString {
    //assumes a word starts with a letter and ends with one not letter
    //once it meets a non-letter it increments counter and starts searching again from next position
    public static int count(String text) {
        int wordCnt = 0;
        
        boolean isWord = false;
        int endIdx = text.length() - 1;
        char[] chs = text.toCharArray();
        for (int i = 0; i <= endIdx; i++) {
            //1.char is a letter, not last 
            if (Character.isLetter(chs[i]) && i != endIdx) {
                isWord = true;
            } else if (!Character.isLetter(chs[i]) && isWord) {
                wordCnt++;
                isWord = false;
            } else if (Character.isLetter(chs[i]) && i == endIdx) {
                wordCnt++;
            }
            //2.char isn't a letter, there have been letters before, cnt
            
            //3.last word
        }
        
        return wordCnt;
    }

    public static void main(String[] args) {
        String text1 = "  this   is  a sentence ";
        String text2 = "a";
        String text3 = " practice   makes   perfect";

        System.out.println(count(text1));
        System.out.println(Arrays.toString(text1.trim().split("\\s+")));
        System.out.println(Arrays.toString(text1.trim().split(" ")));
        System.out.println(count(text2));
        System.out.println(count(text3));
    }
}
