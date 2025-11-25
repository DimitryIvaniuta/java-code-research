package com.code.research.algorithm;

public final class MultiplyStrings43 {
    // Multiply two non-negative integer strings without BigInteger / int conversion
    public static String multiply(String num1, String num2) {
        // quick zero check
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }

        int n = num1.length();
        int m = num2.length();
        // result digits (reverse-accumulated)
        int[] res = new int[n + m];

        // grade-school multiplication from right to left
        for (int i = n - 1; i >= 0; i--) {
            // digit of num1
            int d1 = num1.charAt(i) - '0';
            for (int j = m - 1; j >= 0; j--) {
                // digit of num2
                int d2 = num2.charAt(j) - '0';
                // ones position for this pair
                int pos = i + j + 1;
                // add to existing digit
                int sum = d1 * d2 + res[pos];
                // store ones
                res[pos] = sum % 10;
                // carry to the left
                res[pos - 1] += sum / 10;
            }
        }

        // build string skipping leading zeros
        StringBuilder sb = new StringBuilder();
        int k = 0;
        // skip leading zeros
        while (k < res.length && res[k] == 0) {
            k++;
        }
        // append remaining digits
        for (; k < res.length; k++) {
            sb.append(res[k]);
        }
        // final product
        return sb.toString();
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(multiply("2", "3"));        // 6
        System.out.println(multiply("123", "456"));    // 56088
        System.out.println(multiply("999", "0"));      // 0
    }
}
