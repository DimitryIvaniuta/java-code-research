package com.code.research.datastructures.algorithm.sorting;
import java.util.Arrays;

/**
 * SortingAlgorithms provides static methods for various sorting algorithms.
 *
 * <p>This class includes implementations for:
 * <ul>
 *   <li>Merge Sort: A stable, O(n log n) algorithm.</li>
 *   <li>Quick Sort: An in-place, divide-and-conquer algorithm with average-case O(n log n) performance.</li>
 *   <li>Heap Sort: An in-place algorithm with O(n log n) worst-case performance.</li>
 * </ul>
 *
 * <p>These algorithms are used in real-world applications like data processing, search optimization,
 * and database management.
 *
 * @author Dimitry Ivaniuta
 */
public class SortingAlgorithms {

    /**
     * Sorts the given array using Merge Sort.
     *
     * @param array the array to be sorted.
     * @param <T>   the type of elements in the array; must be Comparable.
     */
    public static <T extends Comparable<? super T>> void mergeSort(T[] array) {
        if (array.length < 2) {
            return;
        }
        int mid = array.length / 2;
        T[] left = Arrays.copyOfRange(array, 0, mid);
        T[] right = Arrays.copyOfRange(array, mid, array.length);
        mergeSort(left);
        mergeSort(right);
        merge(array, left, right);
    }

    /**
     * Merges two sorted subarrays into the original array.
     *
     * @param array the target array to store merged result.
     * @param left  the left sorted subarray.
     * @param right the right sorted subarray.
     * @param <T>   the type of elements.
     */
    private static <T extends Comparable<? super T>> void merge(T[] array, T[] left, T[] right) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < left.length && j < right.length) {
            if (left[i].compareTo(right[j]) <= 0) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }
        while (i < left.length) {
            array[k++] = left[i++];
        }
        while (j < right.length) {
            array[k++] = right[j++];
        }
    }

    /**
     * Sorts the given array using Quick Sort.
     *
     * @param array the array to be sorted.
     * @param <T>   the type of elements in the array; must be Comparable.
     */
    public static <T extends Comparable<? super T>> void quickSort(T[] array) {
        quickSort(array, 0, array.length - 1);
    }

    /**
     * Recursive Quick Sort helper method.
     *
     * @param array the array to sort.
     * @param low   the starting index.
     * @param high  the ending index.
     * @param <T>   the type of elements.
     */
    private static <T extends Comparable<? super T>> void quickSort(T[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    /**
     * Partitions the array using the last element as the pivot.
     *
     * @param array the array to partition.
     * @param low   the starting index.
     * @param high  the ending index.
     * @param <T>   the type of elements.
     * @return the index of the pivot element after partition.
     */
    private static <T extends Comparable<? super T>> int partition(T[] array, int low, int high) {
        T pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j].compareTo(pivot) <= 0) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, high);
        return i + 1;
    }

    /**
     * Swaps two elements in the array.
     *
     * @param array the array.
     * @param i     index of the first element.
     * @param j     index of the second element.
     * @param <T>   the type of elements.
     */
    private static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Sorts the given array using Heap Sort.
     *
     * @param array the array to be sorted.
     * @param <T>   the type of elements in the array; must be Comparable.
     */
    public static <T extends Comparable<? super T>> void heapSort(T[] array) {
        int n = array.length;
        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }
        // One by one extract an element from heap
        for (int i = n - 1; i > 0; i--) {
            swap(array, 0, i);
            heapify(array, i, 0);
        }
    }

    /**
     * Ensures the subtree rooted at index i satisfies the heap property.
     *
     * @param array the array representing the heap.
     * @param n     the size of the heap.
     * @param i     the root index of the subtree.
     * @param <T>   the type of elements in the array.
     */
    private static <T extends Comparable<? super T>> void heapify(T[] array, int n, int i) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1; // left child index
        int right = 2 * i + 2; // right child index

        if (left < n && array[left].compareTo(array[largest]) > 0) {
            largest = left;
        }
        if (right < n && array[right].compareTo(array[largest]) > 0) {
            largest = right;
        }
        if (largest != i) {
            swap(array, i, largest);
            heapify(array, n, largest);
        }
    }

    /**
     * Main method demonstrating the usage of various sorting algorithms.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        Integer[] array1 = {38, 27, 43, 3, 9, 82, 10};
        Integer[] array2 = Arrays.copyOf(array1, array1.length);
        Integer[] array3 = Arrays.copyOf(array1, array1.length);

        System.out.println("Original Array: " + Arrays.toString(array1));

        mergeSort(array1);
        System.out.println("After Merge Sort: " + Arrays.toString(array1));

        quickSort(array2);
        System.out.println("After Quick Sort: " + Arrays.toString(array2));

        heapSort(array3);
        System.out.println("After Heap Sort: " + Arrays.toString(array3));
    }
}
