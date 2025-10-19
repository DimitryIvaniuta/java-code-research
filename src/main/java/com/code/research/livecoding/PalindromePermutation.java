package com.code.research.livecoding;

public class PalindromePermutation {
    // Returns true if some permutation of s can be a palindrome.
    // Letters only (a-z) are considered; spaces are ignored; case-insensitive.
    public static boolean canPermutePalindrome(String s) {
        int oddMask = 0; // 26-bit parity mask for a..z
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') continue;                  // ignore spaces
            c = Character.toLowerCase(c);
            if (c >= 'a' && c <= 'z') {
                int bit = 1 << (c - 'a');
                oddMask ^= bit;                      // flip parity for this letter
            }
        }
        // Valid if at most one bit is set (0 or a single odd count)
        return oddMask == 0 || (oddMask & (oddMask - 1)) == 0;
    }

    // Tiny demo
    public static void main(String[] args) {
        System.out.println(canPermutePalindrome("tact coa"));     // true  ("taco cat")
        System.out.println(canPermutePalindrome("aabb"));         // true
        System.out.println(canPermutePalindrome("abc"));          // false
        System.out.println(canPermutePalindrome("Aa Bb  "));      // true
        System.out.println(canPermutePalindrome("zzzz z"));       // true
    }
}