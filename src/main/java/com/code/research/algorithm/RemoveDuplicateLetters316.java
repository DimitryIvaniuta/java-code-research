package com.code.research.algorithm;

public class RemoveDuplicateLetters316 {
    // Return the lexicographically smallest string with each letter once
    public static String removeDuplicateLetters(String s) {
        int n = s.length();
        // last index where each letter appears
        int[] last = new int[26];
        for (int i = 0; i < n; i++) last[s.charAt(i) - 'a'] = i;

        // already in the stack/result?
        boolean[] seen = new boolean[26];
        // acts as a monotonic stack of chars
        StringBuilder st = new StringBuilder();

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            int k = c - 'a';
            // skip if we already placed this char
            if (seen[k]) {
                continue;
            }

            // while top of stack is lexicographically bigger than c
            // and we can still see that top later (so it's safe to pop),
            // pop it to get a smaller lexicographic result
            while (!st.isEmpty()) {
                char top = st.charAt(st.length() - 1);
                // already <= c → keep order
                if (top <= c) {
                    break;
                }
                // top won't reappear → must keep it
                if (last[top - 'a'] < i) {
                    break;
                }
                // mark popped as not in result
                seen[top - 'a'] = false;
                // pop
                st.deleteCharAt(st.length() - 1);
            }
            // push current char
            st.append(c);
            // mark as in result
            seen[k] = true;
        }
        return st.toString();
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(removeDuplicateLetters("bcabc"));    // "abc"
        System.out.println(removeDuplicateLetters("cbacdcbc")); // "acdb"
    }
}
