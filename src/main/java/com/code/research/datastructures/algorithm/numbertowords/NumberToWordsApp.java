package com.code.research.datastructures.algorithm.numbertowords;

import lombok.extern.slf4j.Slf4j;

import static com.code.research.datastructures.algorithm.numbertowords.NumberToWords.numberToWords;

@Slf4j
public class NumberToWordsApp {

    // Quick test of the examples and some edge cases
    public static void main(String[] args) {
        int[] tests = {3, 10, 11, 123, 12345, 1234567, 0, 1000010};
        for (int n : tests) {
            log.info("{} → \"{}\"", n, numberToWords(n));
        }
    }

}
