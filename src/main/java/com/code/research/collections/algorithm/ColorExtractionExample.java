package com.code.research.collections.algorithm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ColorExtractionExample {

    public static void main(String[] args) {
        // Suppose we have an ARGB color value.
        int argb = 0xFF3366CC; // Alpha=0xFF, Red=0x33, Green=0x66, Blue=0xCC

        // Extract each component using bit shifts and bit masks.
        int alpha = (argb >> 24) & 0xFF; // Shift right 24 bits, then mask.
        int red   = (argb >> 16) & 0xFF; // Shift right 16 bits, then mask.
        int green = (argb >> 8)  & 0xFF; // Shift right 8 bits, then mask.
        int blue  = argb & 0xFF;         // Mask only.

        log.info(String.format("ARGB: %02X, %02X, %02X, %02X", alpha, red, green, blue));
    }

}
