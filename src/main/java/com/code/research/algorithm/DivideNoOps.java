package com.code.research.algorithm;

public final class DivideNoOps {
    // Returns quotient of dividend / divisor without using '/' or '%'.
    public static int divide(int dividend, int divisor) {
        if (divisor == 0) {
            throw new IllegalArgumentException("divisor == 0");
        }
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE; // clamp overflow
        }

        long a = Math.abs((long) dividend);
        long b = Math.abs((long) divisor);
        int sign = ((dividend ^ divisor) < 0) ? -1 : 1;

        int q = 0;
        for (int i = 31; i >= 0; i--) {
            if ((a >> i) >= b) {          // can subtract (b << i)
                a -= (b << i);
                q += (1 << i);
            }
        }
        return sign > 0 ? q : -q;
    }

    // Optional: remainder (same constraint: no '%' or '/')
    public static int remainder(int dividend, int divisor) {
        if (divisor == 0) {
            throw new IllegalArgumentException("divisor == 0");
        }
        long a = Math.abs((long) dividend);
        long b = Math.abs((long) divisor);

        for (int i = 31; i >= 0; i--) {
            if ((a >> i) >= b) a -= (b << i);
        }
        // Remainder has the sign of the dividend in Java
        return (dividend < 0) ? (int) -a : (int) a;
    }

    public static void main(String[] args) {
        System.out.println(divide(10, 3));   // 3
        System.out.println(divide(-7, 2));   // -3
        System.out.println(remainder(10, 3)); // 1
        System.out.println(remainder(-7, 2)); // -1
    }
}
