package com.code.research.datastructures.algorithm.search;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BinarySearchRecursive {

    /**
     * Performs a recursive binary search on a sorted array.
     *
     * @param array  the sorted array of integers.
     * @param target the target value to search for.
     * @param left   the left index of the subarray to search.
     * @param right  the right index of the subarray to search.
     * @return the index of the target if found; otherwise, -1.
     */
    public static int binarySearchRecursive(int[] array, int target, int left, int right) {
        if (left > right) {
            return -1; // Base case: target is not found.
        }

        int mid = left + (right - left) / 2;
        if (array[mid] == target) {
            return mid;
        } else if (array[mid] < target) {
            return binarySearchRecursive(array, target, mid + 1, right);
        } else {
            return binarySearchRecursive(array, target, left, mid - 1);
        }
    }

    public static void main(String[] args) {
        int[] sortedArray = {1, 3, 5, 7, 9, 11, 13, 15};
        int target = 7;
        int index = binarySearchRecursive(sortedArray, target, 0, sortedArray.length - 1);
        log.info("Recursive Binary Search: Target {} found at index {}", target, index);
    }
}
