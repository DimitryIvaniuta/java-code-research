package com.code.research.datastructures.algorithm.sorting;

import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;

@Slf4j
public class SortingAlgorithmsApplication {

    /**
     * Main method demonstrating the usage of various sorting algorithms.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        Integer[] array1 = {38, 27, 43, 3, 9, 82, 10};
        Integer[] array2 = Arrays.copyOf(array1, array1.length);
        Integer[] array3 = Arrays.copyOf(array1, array1.length);

        log.info("Original Array: {}", Arrays.toString(array1));

        SortingAlgorithms.mergeSort(array1);
        log.info("After Merge Sort: {}", Arrays.toString(array1));

        SortingAlgorithms.quickSort(array2);
        log.info("After Quick Sort: {}", Arrays.toString(array2));

        SortingAlgorithms.heapSort(array3);
        log.info("After Heap Sort: {}", Arrays.toString(array3));
    }
    
}
