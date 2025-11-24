package com.code.research.algorithm;

import java.util.*;

public final class RemoveDuplicateLettersWithMap {
    // Lexicographically smallest string where each letter appears once
    public static String removeDuplicateLetters(String s) {
        // last index for each char
        Map<Character, Integer> last = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            last.put(s.charAt(i), i);
        }

        // acts like a char stack
        StringBuilder stack = new StringBuilder();
        // chars already in stack/result
        Set<Character> inRes = new HashSet<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // skip duplicates once placed
            if (inRes.contains(c)) {
                continue;
            }

            // while top is larger than current and appears again later → pop it to get smaller lexicographic order
            while (!stack.isEmpty()) {
                char top = stack.charAt(stack.length() - 1);
                // good order already
                if (top <= c) {
                    break;
                }
                // last occurrence of 'top'
                Integer lastTop = last.get(top);
                // top won't reappear → must keep
                if (lastTop == null || lastTop <= i) {
                    break;
                }
                stack.deleteCharAt(stack.length() - 1);
                inRes.remove(top);
            }

            stack.append(c);
            inRes.add(c);
        }
        return stack.toString();
    }

    public static void main(String[] args) {
        System.out.println(removeDuplicateLetters("bcabc"));    // "abc"
        System.out.println(removeDuplicateLetters("cbacdcbc")); // "acdb"
    }
}
