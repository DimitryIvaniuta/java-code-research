package com.code.research.algorithm;

import java.util.ArrayDeque;
import java.util.Deque;

public final class ValidParenthesesSimple {
    // Obvious approach: push opens; on close, top must be the matching opener.
    public static boolean isValid(String s) {
        Deque<Character> st = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            switch (c) {
                case '(':
                case '[':
                case '{':
                    st.push(c);
                    break;           // push open
                case ')':
                    if (st.isEmpty() || st.pop() != '(') return false;
                    break;
                case ']':
                    if (st.isEmpty() || st.pop() != '[') return false;
                    break;
                case '}':
                    if (st.isEmpty() || st.pop() != '{') return false;
                    break;
                default:
                    return false;                                      // only brackets allowed
            }
        }
        return st.isEmpty();                                                // no unmatched opens
    }

    // Return true if every opening bracket is closed by the same type in correct order
    public static boolean isValid2(String s) {
        // stack for opens
        ArrayDeque<Character> st = new ArrayDeque<>();
        // scan characters
        for (int i = 0; i < s.length(); i++) {
            // current char
            char c = s.charAt(i);
            // opening → push
            if (c == '(' || c == '[' || c == '{') {
                st.push(c);
            } else {
                // closing → must match stack top
                // no opener to match
                if (st.isEmpty()) {
                    return false;
                }
                char o = st.pop();
                // get last opener
                // mismatch pairs
                if ((c == ')' && o != '(')
                        || (c == ']' && o != '[')
                        || (c == '}' && o != '{')) {
                    return false;
                }
            }
        }
        // valid if no unmatched openers
        return st.isEmpty();
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(isValid("()"));       // true
        System.out.println(isValid("()[]{}"));   // true
        System.out.println(isValid("(]"));       // false
        System.out.println(isValid("([])"));     // true
        System.out.println(isValid("([)]"));     // false
    }
}
