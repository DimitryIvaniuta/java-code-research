package com.code.research.algorithm;

public final class StringToIntegerAtoi {
    // Convert string to 32-bit signed integer (atoi)
    public static int myAtoi(String s) {
        // accumulated value (as positive), with overflow checks
        int res = 0;
        //  2147483647
        final int MAX = Integer.MAX_VALUE;
        // -2147483648
        final int MIN = Integer.MIN_VALUE;

        // total length
        int n = s.length();
        // scan pointer
        int i = 0;
        // sign - default positive
        int sign = 1;

        // 1) skip leading spaces
        while (i < n && s.charAt(i) == ' ') {
            i++;
        }

        if (i < n && (s.charAt(i) == '+' || s.charAt(i) == '-')) {
            sign = (s.charAt(i) == '-') ? -1 : 1;
            i++;
        }

        // 2) read digits; stop at first non-digit
        while (i < n) {
            char c = s.charAt(i);
            // stop on non-digit
            if (c < '0' || c > '9') {
                break;
            }
            // current digit 0..9
            int d = c - '0';

            // 3) overflow/underflow clamp before multiplying by 10 and adding d
            // If res > (MAX - d) / 10, then res*10 + d would overflow
            if (res > (MAX - d) / 10) {
                // clamp per problem
                return (sign == 1) ? MAX : MIN;
            }
            // safe accumulate
            res = res * 10 + d;
            // advance
            i++;
        }
        // 4) apply sign (fits due to clamp above)
        return sign * res;
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(myAtoi("42"));         // 42
        System.out.println(myAtoi("   -042"));    // -42
        System.out.println(myAtoi("1337c0d3"));   // 1337
        System.out.println(myAtoi("0-1"));        // 0
        System.out.println(myAtoi("words and 987")); // 0
        System.out.println(myAtoi("2147483648")); // 2147483647 (clamped)
        System.out.println(myAtoi("-2147483649"));// -2147483648 (clamped)
    }
}
