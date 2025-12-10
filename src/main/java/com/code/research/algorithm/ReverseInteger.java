package com.code.research.algorithm;

public class ReverseInteger {

    // Reverse Integer
    public static int reverse(int x) {
        // reversed number we're building
        int rev = 0;
        while (x != 0) {
            // take last digit
            int d = x % 10;
            // drop last digit
            x /= 10;
            System.out.printf("X: %d : d: %d \n", x, d);
            // Overflow checks before rev*10 + d (32-bit signed range)
            if (rev > Integer.MAX_VALUE / 10 || (rev == Integer.MAX_VALUE / 10 && d > 7)) {
                return 0;
            }
            if (rev < Integer.MIN_VALUE / 10 || (rev == Integer.MIN_VALUE / 10 && d < -8)) {
                return 0;
            }
            // safe to append digit
            rev = rev * 10 + d;
        }
        return rev;
    }

    public static void main(String[] args) {
        int[] tests = {123, -123, 120, 0, 1534236469, -2147483412};
        for (int x : tests) {
            System.out.printf("reverse(%d) = %d%n", x, reverse(x));
        }

        // quick assertions (optional)
        assert reverse(123) == 321;
        assert reverse(-123) == -321;
        assert reverse(120) == 21;
        assert reverse(1534236469) == 0;      // overflow -> 0
        assert reverse(-2147483412) == -2143847412;
    }
}
