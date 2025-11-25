package com.code.research.algorithm;

public final class RotateString {
    // Return true iff s can be rotated (left shifts) to equal goal
    public static boolean rotateString(String s, String goal) {
        // different lengths can never match
        if (s.length() != goal.length()) {
            return false;
        }
        // both empty → trivially equal
        if (s.isEmpty()) {
            return true;
        }
        // all left-rotations of s appear as substrings of s+s
        String ss = s + s;
        // check if goal is one of those rotations
        return ss.contains(goal);
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(rotateString("abcde", "cdeab")); // true
        System.out.println(rotateString("abcde", "abced")); // false
    }
}
