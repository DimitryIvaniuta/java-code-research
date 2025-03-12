package com.code.research.collections.algorithm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MultiplyDivideExample {

    public static void main(String[] args) {
        int number = 8;

        // Multiply by 2 using left shift (n * 2^1)
        int multiplied = number << 1;
        log.info("{} multiplied by 2 is {}", number, multiplied);

        // Multiply by 8 using left shift (n * 2^3)
        int multipliedBy8 = number << 3;
        log.info("{} multiplied by 8 is {}", number, multipliedBy8);

        // Divide by 2 using right shift (n / 2^1)
        int divided = number >> 1;
        log.info("{} divided by 2 is {}", number, divided);

        // Divide by 4 using right shift (n / 2^2)
        int dividedBy4 = number >> 2;
        log.info("{} divided by 4 is {}", number, dividedBy4);
    }

}
