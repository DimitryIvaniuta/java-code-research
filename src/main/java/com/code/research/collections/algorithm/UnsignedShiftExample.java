package com.code.research.collections.algorithm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnsignedShiftExample {

    public static void main(String[] args) {
        int negativeNumber = -16;
        log.info("Original negative number: {}", negativeNumber);

        // Signed right shift (>>): preserves the sign bit
        int signedShift = negativeNumber >> 2;
        log.info("Signed right shift of -16 by 2: {}", signedShift);

        // Unsigned right shift (>>>): fills with zero on the left
        int unsignedShift = negativeNumber >>> 2;
        log.info("Unsigned right shift of -16 by 2: {}", unsignedShift);
    }

}
