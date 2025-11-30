package com.code.research.algorithm;

public class PalindromePermutationSimple {
    public static boolean canPermutePalindrome(String s) {
        int[] freq = new int[26];
        int odd = 0;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            c = Character.toLowerCase(c);
            if(c == ' ') continue;
            if(c > 'a' && c <= 'z'){
                int idx = c-'a';
                freq[idx]++;
                if((freq[idx] & 1) == 1){
                    odd++;
                } else {
                    odd--;
                }
            }
        }
        return odd <=1;
    }

    public static void main(String[] args) {
        System.out.println(canPermutePalindrome("tact coa")); // true ("taco cat")
        System.out.println(canPermutePalindrome("aabb"));     // true
        System.out.println(canPermutePalindrome("abc"));      // false
    }
}
