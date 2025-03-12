package com.code.research.collections.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class ArrayShiftExample {

    public static void main(String[] args) {
        // Original array of numbers.
        int[] numbers = {10, 20, 30, 40, 50};
        log.info("Original array: " + Arrays.toString(numbers));

        // Suppose we want to remove the element at index 2 (value 30).
        int removeIndex = 2;
        int length = numbers.length;

        // Shift elements to the left manually.
        for (int i = removeIndex; i < length - 1; i++) {
            numbers[i] = numbers[i + 1];
        }

        // Optionally, clear the last element (set it to 0 or any default value).
        numbers[length - 1] = 0;

        log.info("Array after removal: " + Arrays.toString(numbers));
    }

}
