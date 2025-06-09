package com.code.research.algorithm.test;

public class StringsAnagrams {

    public boolean isAnagram(String s1, String s2){
        if(s1 == null || s2 ==null || s1.length() != s2.length()) return false;
        // ASCII
        int[] counts = new int[256];
        for(int i=0; i<s1.length(); i++){
            counts[s1.charAt(i)]++;
            counts[s2.charAt(i)]++;
        }
        for(int i=0;i<counts.length;i++){
           if(counts[i] != 0){
               return false;
           }
        }
        return true;
    }
}
