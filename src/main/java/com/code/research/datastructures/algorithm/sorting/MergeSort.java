package com.code.research.datastructures.algorithm.sorting;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class MergeSort {

    /**
     * Sorts the given array using the merge sort algorithm.
     *
     * @param array the array to be sorted.
     * @param <T>   the type of elements in the array; must be Comparable.
     */
    public static <T extends Comparable<? super T>> void mergeSort(T[] array) {
        if (array.length < 2) {
            return; // Base case: arrays with 0 or 1 element are already sorted.
        }
        int mid = array.length / 2;
        T[] left = Arrays.copyOfRange(array, 0, mid);
        T[] right = Arrays.copyOfRange(array, mid, array.length);

        // Recursively sort both halves.
        mergeSort(left);
        mergeSort(right);

        // Merge the sorted halves.
        merge(array, left, right);
    }

    /**
     * Merges two sorted subarrays (left and right) into the original array.
     *
     * @param array the target array to store merged result.
     * @param left  the left sorted subarray.
     * @param right the right sorted subarray.
     * @param <T>   the type of elements.
     */
    private static <T extends Comparable<? super T>> void merge(T[] array, T[] left, T[] right) {
        int i = 0, j = 0, k = 0;

        // Merge elements while both subarrays have remaining items.
        while (i < left.length && j < right.length) {
            // If left[i] is less than or equal to right[j], add left[i] to the array.
            // This preserves the order of equal elements, ensuring stability.
            if (left[i].compareTo(right[j]) <= 0) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }

        // Copy any remaining elements of left.
        while (i < left.length) {
            array[k++] = left[i++];
        }
        // Copy any remaining elements of right.
        while (j < right.length) {
            array[k++] = right[j++];
        }
    }

    public static void main(String[] args) {
        Integer[] array = {38, 27, 43, 3, 9, 82, 10};
        log.info("Original Array: {}", Arrays.toString(array));
        mergeSort(array);
        log.info("Sorted Array: {}", Arrays.toString(array));
    }
}
