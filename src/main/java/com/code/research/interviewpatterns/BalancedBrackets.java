package com.code.research.interviewpatterns;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

/**
 * “I will solve it with a stack.
 *  When I see an opening bracket (, {, [, I push it to the stack. When I see a closing bracket ), }, ],
 *  I check the stack is not empty and the top opening bracket matches this closing one.
 *  If it’s empty or mismatch, I return false. At the end, the stack must be empty.
 *  Time complexity is O(n) and space is O(n).”
 */
public final class BalancedBrackets {

    // Returns true if s has valid balanced brackets: (), {}, []
    // Ignores non-bracket characters (safe for mixed input).
    public static boolean isBalanced(String s) {
        if (s == null) return true; // or false, depends on requirement

        Map<Character, Character> closeToOpen = Map.of(
                ')', '(',
                '}', '{',
                ']', '['
        );

        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            // opening
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
                continue;
            }

            // closing
            Character expectedOpen = closeToOpen.get(ch);
            if (expectedOpen != null) {
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if (top != expectedOpen) return false;
            }

            // non-bracket char -> ignore
        }

        return stack.isEmpty();
    }

    // quick manual checks
    public static void main(String[] args) {
        System.out.println(isBalanced("()"));           // true
        System.out.println(isBalanced("([]){}"));       // true
        System.out.println(isBalanced("(]"));           // false
        System.out.println(isBalanced("(()"));          // false
        System.out.println(isBalanced("a(b)c"));        // true (ignores letters)
    }
}
