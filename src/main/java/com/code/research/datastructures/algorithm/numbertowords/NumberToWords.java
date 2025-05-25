package com.code.research.datastructures.algorithm.numbertowords;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NumberToWords {

    private static final String[] BELOW_TWENTY = {
            "", "One", "Two", "Three", "Four", "Five", "Six",
            "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve",
            "Thirteen", "Fourteen", "Fifteen", "Sixteen",
            "Seventeen", "Eighteen", "Nineteen"
    };

    private static final String[] TENS = {
            "", "Ten", "Twenty", "Thirty", "Forty",
            "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
    };

    private static final String[] THOUSANDS = {
            "", "Thousand", "Million", "Billion"
    };

    /**
     * Converts a non-negative integer to its English words representation.
     *
     * @param num the number to convert (0 ≤ num ≤ 2^31–1)
     * @return the English words, e.g. 12345 → "Twelve Thousand Three Hundred Forty Five"
     */
    public static String numberToWords(int num) {
        if (num == 0) {
            return "Zero";
        }

        String words = "";
        int chunkIndex = 0;  // 0 → "", 1 → "Thousand", 2 → "Million", 3 → "Billion"

        while (num > 0) {
            int chunk = num % 1000;
            log.info("Num: {} chunk: {}", num, chunk);
            if (chunk != 0) {
                String chunkWords = helper(chunk).trim();
                words = chunkWords +
                        (THOUSANDS[chunkIndex].isEmpty() ? "" : " " + THOUSANDS[chunkIndex]) +
                        (words.isEmpty() ? "" : " " + words);
            }
            num /= 1000;
            chunkIndex++;
        }

        return words;
    }

    // Helper to convert a number < 1000 into words (no trailing spaces)
    private static String helper(int num) {
        if (num == 0) {
            return "";
        } else if (num < 20) {
            return BELOW_TWENTY[num];
        } else if (num < 100) {
            int tenUnit = num / 10;
            int remainder = num % 10;
            return TENS[tenUnit] +
                    (remainder == 0 ? "" : " " + BELOW_TWENTY[remainder]);
        } else {
            int hundreds = num / 100;
            int remainder = num % 100;
            return BELOW_TWENTY[hundreds] + " Hundred" +
                    (remainder == 0 ? "" : " " + helper(remainder));
        }
    }

}
