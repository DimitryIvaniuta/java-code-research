package com.code.research.algorithm;

import java.util.Arrays;

public final class IsomorphicStrings {
    // Return true if s and t are isomorphic (one-to-one char mapping, order preserved)
    public static boolean isIsomorphic(String s, String t) {
        // lengths must match
        if (s.length() != t.length()) {
            return false;
        }
        // map from s -> t (ASCII)
        int[] st = new int[256];
        // map from t -> s (reverse)
        int[] ts = new int[256];
        Arrays.fill(st, -1);
        Arrays.fill(ts, -1);

        for (int i = 0; i < s.length(); i++) {
            // current char from s
            int a = s.charAt(i);
            // current char from t
            int b = t.charAt(i);

            // unseen pair → bind both ways
            if (st[a] == -1 && ts[b] == -1) {
                st[a] = b;
                ts[b] = a;
            } else {
                // mapping conflict
                if (st[a] != b || ts[b] != a) {
                    return false;
                }
            }
        }
        // all positions consistent
        return true;
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(isIsomorphic("egg", "add"));    // true
        System.out.println(isIsomorphic("foo", "bar"));    // false
        System.out.println(isIsomorphic("paper", "title"));// true
    }
}
