package com.code.research.datastructures.arrays;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageProcessor {

    public static void main(String[] args) {
        // 2D array representing a 3x3 grayscale image
        int[][] image = {
                {255, 128, 64},
                {128, 64, 32},
                {64, 32, 0}
        };

        log.info("Grayscale Image Matrix:");
        for (int[] ints : image) {
            for (int anInt : ints) {
                log.info("{}\t", anInt);
            }
            log.info("");
        }
    }

}
