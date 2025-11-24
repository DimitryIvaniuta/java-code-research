package com.code.research.algorithm;

public final class ValidPalindrome125 {
    // Return true if s is a palindrome after keeping only letters/digits and ignoring case
    public static boolean isPalindrome(String s) {
        int l = 0;
        // two pointers from both ends
        int r = s.length() - 1;
        while (l < r) {
            // skip non-alnum on left
            while (l < r && !Character.isLetterOrDigit(s.charAt(l))) {
                l++;
            }
            // skip non-alnum on right
            while (l < r && !Character.isLetterOrDigit(s.charAt(r))) {
                r--;
            }
            // normalize left char
            char a = Character.toLowerCase(s.charAt(l));
            // normalize right char
            char b = Character.toLowerCase(s.charAt(r));
            // mismatch => not palindrome
            if (a != b) {
                return false;
            }
            // move inward
            l++;
            r--;
        }
        // all matched
        return true;
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(isPalindrome("A man, a plan, a canal: Panama")); // true
        System.out.println(isPalindrome("race a car"));                      // false
        System.out.println(isPalindrome(" "));                               // true
    }
}
