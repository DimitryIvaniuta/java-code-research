package com.code.research.datastructures.algorithm.search;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BinarySearchIterative {

    /**
     * Performs an iterative binary search on a sorted array.
     *
     * @param array   the sorted array of integers.
     * @param target  the target value to search for.
     * @return the index of the target if found; otherwise, -1.
     */
    public static int binarySearch(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2; // Avoids overflow
            if (array[mid] == target) {
                return mid;
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1; // Target not found
    }

    public static void main(String[] args) {
        int[] sortedArray = {1, 3, 5, 7, 9, 11, 13, 15};
        int target = 7;
        int index = binarySearch(sortedArray, target);
        log.info("Iterative Binary Search: Target {} found at index {}", target, index);
    }
}
