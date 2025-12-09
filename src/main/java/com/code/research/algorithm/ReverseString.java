package com.code.research.algorithm;

public class ReverseString {
    // Two-pointer in-place reverse, O(n) time, O(1) extra space
    public void reverseString(char[] s) {
        int i = 0;
        // ends of the array
        int j = s.length - 1;
        while (i < j) {
            // swap s[i] and s[j]
            char t = s[i];
            s[i++] = s[j];
            s[j--] = t;
        }
    }

}
