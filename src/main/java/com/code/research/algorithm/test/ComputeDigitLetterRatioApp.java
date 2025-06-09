package com.code.research.algorithm.test;

public class ComputeDigitLetterRatioApp {

    /**
     * Computes the ratio of (sum of all digit characters) to (count of all letter characters)
     * in the given string, rounding to the nearest integer.
     *
     * @param input the string to analyze
     * @return the rounded ratio (sum of digits) / (letter count)
     * @throws IllegalArgumentException if input is null, empty, or contains no letters
     */
    public static int computeDigitLetterRatio(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        int sumOfDigits = 0;
        int letterCount = 0;

        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                sumOfDigits += c - '0';
            } else if (Character.isLetter(c)) {
                letterCount++;
            }
            // other characters are ignored
        }

        if (letterCount == 0) {
            throw new IllegalArgumentException("Input must contain at least one letter");
        }

        double ratio = (double) sumOfDigits / letterCount;
        return (int) Math.round(ratio);
    }

    public int computeRation(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }
        int sumOfDigits = 0;
        int letterCount = 0;

        for(char c: input.toCharArray()) {
            if(Character.isDigit(c)){
                sumOfDigits += c - '0';
            } else if(Character.isLetter(c)){
                letterCount++;
            }
        }
        double ratio = (double) sumOfDigits / (letterCount > 0 ? letterCount : 1);
        return (int) Math.round(ratio);
    }

    public static void main(String[] args) {
        String[] tests = {
                "abc123ab12",   // sum=1+2+3+1+2=9, letters=5 → 9/5=1.8 → 2
                "a1b2c3",       // sum=6, letters=3 → 2.0 → 2
                "12345",        // no letters → exception
                "abcd",         // sum=0, letters=4 → 0.0 → 0
                "",             // empty → exception
                null            // null → exception
        };

        for (String test : tests) {
            try {
                int result = computeDigitLetterRatio(test);
                System.out.printf("Input: %-12s → Ratio: %d%n", test, result);
            } catch (IllegalArgumentException e) {
                System.out.printf("Input: %-12s → Error: %s%n", test, e.getMessage());
            }
        }
    }
}
