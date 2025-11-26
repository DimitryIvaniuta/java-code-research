package com.code.research.algorithm;

import java.util.*;

/**
 * Must-know live-coding patterns with Java Set.
 * Short, obvious, and interview-ready.
 */
public final class SetsLiveCoding {

    /* 1) Contains Duplicate (classic) */
    public static boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int x : nums) {
            if (!seen.add(x)) {
                return true; // add returns false if x already present
            }
        }
        return false;
    }

    /* 2) Remove duplicates while keeping order (strings) */
    public static String dedupeKeepOrder(String s) {
        Set<Character> seen = new HashSet<>();
        StringBuilder out = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (seen.add(c)) {
                out.append(c); // append first occurrence only
            }
        }
        return out.toString();
    }

    /* 3) Intersection of two int arrays (unique) */
    public static int[] intersection(int[] a, int[] b) {
        Set<Integer> S = new HashSet<>();
        for (int x : a) {
            S.add(x);
        }
        Set<Integer> ans = new HashSet<>();
        for (int y : b) {
            if (S.contains(y)) {
                ans.add(y);
            }
        }
        return ans.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    /* 4) Union of two int arrays (unique) */
    public static int[] union(int[] a, int[] b) {
        Set<Integer> S = new HashSet<>();
        for (int x : a) {
            S.add(x);
        }
        for (int y : b) {
            S.add(y);
        }
        return S.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    /* 5) Difference A \ B (unique) */
    public static int[] difference(int[] A, int[] B) {
        Set<Integer> sA = new HashSet<>();
        for (int x : A) {
            sA.add(x);
        }
        for (int y : B) {
            sA.remove(y);     // remove overlap
        }
        return sA.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    /* 6) Symmetric Difference (elements in A or B but not both) */
    public static int[] symmetricDifference(int[] A, int[] B) {
        Set<Integer> s = new HashSet<>();
        Set<Integer> seen = new HashSet<>();
        for (int x : A) {
            if (!s.add(x)) {
                seen.add(x);
            }
        }   // seen duplicates within A (not necessary, but harmless)
        for (int y : B) {
            if (!s.add(y)) {
                s.remove(y);
            }
        } // toggle membership
        return s.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    /* 7) Longest Consecutive Sequence (LeetCode 128) — O(n) with HashSet */
    public static int longestConsecutive(int[] nums) {
        Set<Integer> S = new HashSet<>();
        for (int x : nums) {
            S.add(x);
        }
        int best = 0;
        for (int x : nums) {
            if (!S.contains(x - 1)) {     // start of a run
                int y = x;
                while (S.contains(y)) {
                    y++; // count up
                }
                best = Math.max(best, y - x);
            }
        }
        return best;
    }

    /* 8) Two Sum (existence only) — O(n) set */
    public static boolean twoSumExists(int[] nums, int target) {
        Set<Integer> S = new HashSet<>();
        for (int x : nums) {
            if (S.contains(target - x)) {
                return true; // found complement
            }
            S.add(x);
        }
        return false;
    }

    /* 9) First Repeated Character in a string (or '\0' if none) */
    public static char firstRepeatedChar(String s) {
        Set<Character> seen = new HashSet<>();
        for (char c : s.toCharArray()) {
            if (!seen.add(c)) {
                return c; // first that fails to add
            }
        }
        return '\0';
    }

    /* 10) Valid Sudoku row/col/box using Set “seen” signatures */
    public static boolean isValidSudoku(char[][] board) {
        Set<String> seen = new HashSet<>();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char ch = board[r][c];
                if (ch == '.') {
                    continue;
                }
                String row = "r" + r + ch;
                String col = "c" + c + ch;
                String box = "b" + (r / 3) + (c / 3) + ch;
                if (!seen.add(row) || !seen.add(col) || !seen.add(box)) {
                    return false; // duplicate in row/col/box
                }
            }

        }
        return true;
    }

    /* 11) Pangram check (letters a–z appear at least once) */
    public static boolean isPangram(String s) {
        Set<Character> S = new HashSet<>();
        for (char c : s.toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                S.add(c);
            }
        }
        return S.size() == 26;
    }

    /* 12) Unique Morse Code Words (LeetCode 804) */
    public static int uniqueMorse(String[] words) {
        String[] code = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..",
                ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.",
                "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
        Set<String> S = new HashSet<>();
        for (String w : words) {
            StringBuilder sb = new StringBuilder();
            for (char ch : w.toCharArray()) sb.append(code[ch - 'a']);
            S.add(sb.toString());
        }
        return S.size();
    }

    /* 13) Subarray with sum = 0 exists? (prefix sums set) */
    public static boolean hasZeroSumSubarray(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        int pref = 0;
        seen.add(0);                        // empty prefix as 0
        for (int x : nums) {
            pref += x;
            if (!seen.add(pref)) {
                return true; // same prefix again ⇒ subarray sum zero
            }
        }
        return false;
    }

    /* 14) Happy Number (detect cycle with set) */
    public static boolean isHappy(int n) {
        Set<Integer> seen = new HashSet<>();
        while (n != 1 && seen.add(n)) {     // loop until 1 or cycle
            int s = 0, x = n;
            while (x > 0) {
                int d = x % 10;
                s += d * d;
                x /= 10;
            }
            n = s;
        }
        return n == 1;
    }

    /* 15) Check if any window of size k has duplicates (sliding + set) */
    public static boolean hasDuplicateInWindow(int[] nums, int k) {
        Set<Integer> S = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (!S.add(nums[i])) {
                return true;      // duplicate in window
            }
            if (i >= k) {
                S.remove(nums[i - k]);     // slide: remove outgoing
            }
        }
        return false;
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(containsDuplicate(new int[]{1, 2, 3, 1}));        // true
        System.out.println(dedupeKeepOrder("banana"));                     // "ban"
        System.out.println(Arrays.toString(intersection(new int[]{1, 2, 2}, new int[]{2, 3}))); // [2]
        System.out.println(longestConsecutive(new int[]{100, 4, 200, 1, 3, 2})); // 4
        System.out.println(twoSumExists(new int[]{2, 7, 11, 15}, 9));        // true
        System.out.println(firstRepeatedChar("abcdcaf"));                  // 'c'
        System.out.println(isPangram("The quick brown fox jumps over a lazy dog")); // true
        System.out.println(uniqueMorse(new String[]{"gin", "zen", "gig", "msg"}));     // 2
        System.out.println(hasZeroSumSubarray(new int[]{1, 2, -3, 4}));      // true
        System.out.println(isHappy(19));                                   // true
        System.out.println(hasDuplicateInWindow(new int[]{1, 2, 3, 1, 4}, 3)); // true
    }
}
