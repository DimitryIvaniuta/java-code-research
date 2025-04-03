package com.code.research.datastructures.sorting;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * SortingAlgorithms provides a collection of static methods that implement a variety of
 * sorting algorithms. These include comparison-based sorts (Insertion, Bubble, Selection,
 * QuickSort, MergeSort, HeapSort) and non-comparison sorts (Radix, Counting, Bucket).
 *
 * <p>Each algorithm is implemented with careful consideration of runtime, space usage, and stability,
 * making this class a useful reference for real-world applications and technical interviews.
 */
@Slf4j
public class SortingAlgorithms {

    // =========================================================================================
    // Insertion Sort
    // =========================================================================================
    /**
     * Sorts the given array using the insertion sort algorithm.
     * <p>
     * Time Complexity: O(n^2) worst-case; O(n) best-case when array is already sorted.
     * Insertion sort is stable and efficient for small or nearly sorted arrays.
     *
     * @param arr the array to be sorted.
     */
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            // Move elements greater than key to one position ahead
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // =========================================================================================
    // Bubble Sort
    // =========================================================================================
    /**
     * Sorts the given array using the bubble sort algorithm.
     * <p>
     * Time Complexity: O(n^2) worst-case.
     * Bubble sort is stable but rarely used in production due to its quadratic time.
     *
     * @param arr the array to be sorted.
     */
    public static void bubbleSort(int[] arr) {
        boolean swapped;
        for (int i = 0; i < arr.length - 1; i++) {
            swapped = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap arr[j] and arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    // =========================================================================================
    // Selection Sort
    // =========================================================================================
    /**
     * Sorts the given array using the selection sort algorithm.
     * <p>
     * Time Complexity: O(n^2) in all cases.
     * Selection sort is not stable (unless modified) but is simple to implement.
     *
     * @param arr the array to be sorted.
     */
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            // Find the minimum element in unsorted portion
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex])
                    minIndex = j;
            }
            // Swap the found minimum with the first unsorted element
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    // =========================================================================================
    // QuickSort
    // =========================================================================================
    /**
     * Sorts the given array using the QuickSort algorithm.
     * <p>
     * Time Complexity: Average-case O(n log n), worst-case O(n^2) if pivot selection is poor.
     * QuickSort is typically in-place and cache-friendly but is not stable.
     *
     * @param arr the array to be sorted.
     */
    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * Helper method to perform QuickSort recursively.
     *
     * @param arr  the array to be sorted.
     * @param low  the starting index.
     * @param high the ending index.
     */
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    /**
     * Partitions the array around a pivot element and returns the pivot's final index.
     *
     * @param arr  the array to partition.
     * @param low  the starting index.
     * @param high the ending index.
     * @return the index of the pivot element after partition.
     */
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    // =========================================================================================
    // MergeSort
    // =========================================================================================
    /**
     * Sorts the given array using the MergeSort algorithm.
     * <p>
     * Time Complexity: O(n log n) in all cases.
     * MergeSort is stable but requires O(n) extra space.
     *
     * @param arr the array to be sorted.
     */
    public static void mergeSort(int[] arr) {
        mergeSort(arr, 0, arr.length - 1);
    }

    /**
     * Helper method to perform MergeSort recursively.
     *
     * @param arr  the array to be sorted.
     * @param left the starting index.
     * @param right the ending index.
     */
    private static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    /**
     * Merges two sorted subarrays of arr.
     *
     * @param arr  the array containing the subarrays.
     * @param left the starting index of the first subarray.
     * @param mid  the ending index of the first subarray.
     * @param right the ending index of the second subarray.
     */
    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }
        while (i < n1) {
            arr[k++] = L[i++];
        }
        while (j < n2) {
            arr[k++] = R[j++];
        }
    }

    // =========================================================================================
    // HeapSort
    // =========================================================================================
    /**
     * Sorts the given array using the HeapSort algorithm.
     * <p>
     * Time Complexity: O(n log n) worst-case.
     * HeapSort is in-place but not stable.
     *
     * @param arr the array to be sorted.
     */
    public static void heapSort(int[] arr) {
        int n = arr.length;
        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        // Extract elements from heap one by one.
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    /**
     * Ensures the subtree rooted at index i satisfies the max-heap property.
     *
     * @param arr the array representing the heap.
     * @param n   the size of the heap.
     * @param i   the root index of the subtree.
     */
    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }
        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    // =========================================================================================
    // Radix Sort (for non-negative integers)
    // =========================================================================================
    /**
     * Sorts the given array using the Radix Sort algorithm.
     * <p>
     * Time Complexity: O(nk), where k is the number of digits.
     * Radix Sort is stable and efficient when k is small.
     *
     * @param arr the array to be sorted.
     */
    public static void radixSort(int[] arr) {
        int max = Arrays.stream(arr).max().orElse(0);
        // Process each digit (LSD Radix Sort)
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, exp);
        }
    }

    /**
     * A helper method for Radix Sort that performs counting sort based on the digit at exp.
     *
     * @param arr the array to be sorted.
     * @param exp the exponent representing the digit position (1, 10, 100, ...).
     */
    private static void countingSortByDigit(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];

        // Store count of occurrences in count[]
        for (int j : arr) {
            int digit = (j / exp) % 10;
            count[digit]++;
        }
        // Change count[i] so that count[i] now contains the actual position of this digit in output[]
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        // Build the output array (stable sorting)
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }
        // Copy the output array to arr
        System.arraycopy(output, 0, arr, 0, n);
    }

    // =========================================================================================
    // Counting Sort (for non-negative integers)
    // =========================================================================================
    /**
     * Sorts the given array using Counting Sort.
     * <p>
     * Time Complexity: O(n + k) where k is the range of the input.
     * Counting Sort is stable and efficient when k is small compared to n.
     *
     * @param arr the array to be sorted.
     * @return a new sorted array.
     */
    public static int[] countingSort(int[] arr) {
        if (arr.length == 0) return arr;
        int max = Arrays.stream(arr).max().getAsInt();
        int[] count = new int[max + 1];
        for (int num : arr) {
            count[num]++;
        }
        int index = 0;
        int[] output = new int[arr.length];
        for (int i = 0; i < count.length; i++) {
            while (count[i]-- > 0) {
                output[index++] = i;
            }
        }
        return output;
    }

    // =========================================================================================
    // Bucket Sort (for doubles in [0, 1))
    // =========================================================================================
    /**
     * Sorts an array of doubles in the range [0, 1) using Bucket Sort.
     * <p>
     * Time Complexity: O(n + k) on average, where k is the number of buckets.
     * Bucket Sort is stable if the individual bucket sort is stable.
     *
     * @param arr the array of doubles to be sorted.
     */
    public static void bucketSort(double[] arr) {
        int n = arr.length;
        if (n <= 0) return;
        @SuppressWarnings("unchecked")
        List<Double>[] buckets = new List[n];
        for (int i = 0; i < n; i++) {
            buckets[i] = new ArrayList<>();
        }
        // Distribute input array values into buckets.
        for (double num : arr) {
            int index = (int) (num * n);
            buckets[index].add(num);
        }
        // Sort each bucket individually and then concatenate results.
        int index = 0;
        for (List<Double> bucket : buckets) {
            Collections.sort(bucket);
            for (double num : bucket) {
                arr[index++] = num;
            }
        }
    }

    /**
     * Utility method to swap two elements in an array.
     *
     * @param arr the array.
     * @param i   the index of the first element.
     * @param j   the index of the second element.
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // =========================================================================================
    // Main method demonstrating all sorting algorithms.
    // =========================================================================================
    public static void main(String[] args) {
        // Example array for comparison-based sorts.
        int[] original = { 29, 10, 14, 37, 13, 3, 7, 20 };

        // Insertion Sort
        int[] arrInsertion = Arrays.copyOf(original, original.length);
        insertionSort(arrInsertion);
        log.info("Insertion Sort: {}", Arrays.toString(arrInsertion));

        // Bubble Sort
        int[] arrBubble = Arrays.copyOf(original, original.length);
        bubbleSort(arrBubble);
        log.info("Bubble Sort: {}", Arrays.toString(arrBubble));

        // Selection Sort
        int[] arrSelection = Arrays.copyOf(original, original.length);
        selectionSort(arrSelection);
        log.info("Selection Sort: {}", Arrays.toString(arrSelection));

        // QuickSort
        int[] arrQuick = Arrays.copyOf(original, original.length);
        quickSort(arrQuick);
        log.info("QuickSort: {}", Arrays.toString(arrQuick));

        // MergeSort
        int[] arrMerge = Arrays.copyOf(original, original.length);
        mergeSort(arrMerge);
        log.info("MergeSort: {}", Arrays.toString(arrMerge));

        // HeapSort
        int[] arrHeap = Arrays.copyOf(original, original.length);
        heapSort(arrHeap);
        log.info("HeapSort: {}", Arrays.toString(arrHeap));

        // Radix Sort (for non-negative integers)
        int[] arrRadix = Arrays.copyOf(original, original.length);
        radixSort(arrRadix);
        log.info("Radix Sort: {}", Arrays.toString(arrRadix));

        // Counting Sort (for non-negative integers)
        int[] arrCounting = countingSort(original);
        log.info("Counting Sort: {}", Arrays.toString(arrCounting));

        // Bucket Sort (for doubles in [0,1))
        double[] arrBucket = {0.78, 0.17, 0.39, 0.26, 0.72, 0.94, 0.21, 0.12, 0.23, 0.68};
        bucketSort(arrBucket);
        log.info("Bucket Sort: {}", Arrays.toString(arrBucket));
    }
    
}
