package com.code.research.datastructures.sorting;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * AlternativeSortingAlgorithms demonstrates several sorting algorithms using various Java Collections.
 *
 * <p>This class contains:
 * <ul>
 *   <li>{@link #mergeSortedArraysGeneric(Object[], Object[])} – Merges two sorted arrays using reflection.</li>
 *   <li>{@link #insertionSortLinkedList(LinkedList)} – Sorts a LinkedList using insertion sort.</li>
 *   <li>{@link #bucketSortUsingQueue(double[])} – Sorts an array of doubles in the range [0, 1) using bucket sort with queues.</li>
 *   <li>{@link #iterativeQuickSortUsingStack(int[])} – Implements QuickSort iteratively using a Stack.</li>
 * </ul>
 */
@Slf4j
public class AlternativeSortingAlgorithms {

    // ================================================================================
    // 1. mergeSortedArraysGeneric: using java.lang.reflect.Array
    // ================================================================================
    /**
     * Merges two sorted arrays into one sorted array using a generic approach.
     * Uses {@code java.lang.reflect.Array.newInstance} to create a new array of the same type.
     *
     * @param left  the first sorted array.
     * @param right the second sorted array.
     * @param <T>   the type of the elements; must implement {@link Comparable}.
     * @return a new sorted array containing all elements from {@code left} and {@code right}.
     */
    public static <T extends Comparable<T>> T[] mergeSortedArraysGeneric(T[] left, T[] right) {
        int n1 = left.length;
        int n2 = right.length;
        @SuppressWarnings("unchecked")
        T[] merged = (T[]) Array.newInstance(left.getClass().getComponentType(), n1 + n2);
        int i = 0, j = 0, k = 0;
        while (i < n1 && j < n2) {
            if (left[i].compareTo(right[j]) <= 0) {
                merged[k++] = left[i++];
            } else {
                merged[k++] = right[j++];
            }
        }
        while (i < n1) merged[k++] = left[i++];
        while (j < n2) merged[k++] = right[j++];
        return merged;
    }

    // ================================================================================
    // 2. insertionSortLinkedList: using java.util.LinkedList
    // ================================================================================
    /**
     * Sorts a {@code LinkedList} using the insertion sort algorithm.
     * This method creates a new sorted list by removing each element from the original list
     * and inserting it into its proper position in the new list.
     *
     * @param list the {@code LinkedList} to be sorted.
     * @param <T>  the type of elements in the list; must implement {@link Comparable}.
     */
    public static <T extends Comparable<T>> void insertionSortLinkedList(LinkedList<T> list) {
        if (list.isEmpty()) return;
        LinkedList<T> sorted = new LinkedList<>();
        while (!list.isEmpty()) {
            T current = list.removeFirst();
            // Find the correct insertion point in sorted list.
            ListIterator<T> iterator = sorted.listIterator();
            boolean inserted = false;
            while (iterator.hasNext()) {
                if (iterator.next().compareTo(current) > 0) {
                    iterator.previous();
                    iterator.add(current);
                    inserted = true;
                    break;
                }
            }
            if (!inserted) {
                sorted.addLast(current);
            }
        }
        // Transfer sorted list back into original list.
        list.addAll(sorted);
    }

    // ================================================================================
    // 3. bucketSortUsingQueue: using java.util.Queue (LinkedList)
    // ================================================================================
    /**
     * Sorts an array of doubles in the range [0, 1) using Bucket Sort.
     * This method uses an array of {@code Queue<Double>} (implemented via LinkedList)
     * as buckets. After distributing elements into buckets, each bucket is sorted individually,
     * and then the buckets are merged back into the original array.
     *
     * @param arr the array of doubles to be sorted.
     */
    public static void bucketSortUsingQueue(double[] arr) {
        int n = arr.length;
        if (n <= 0) return;
        @SuppressWarnings("unchecked")
        Queue<Double>[] buckets = new Queue[n];
        for (int i = 0; i < n; i++) {
            buckets[i] = new LinkedList<>();
        }
        // Distribute input numbers into buckets.
        for (double num : arr) {
            int bucketIndex = (int) (num * n);
            buckets[bucketIndex].offer(num);
        }
        // Sort each bucket individually.
        for (int i = 0; i < n; i++) {
            List<Double> bucketList = new ArrayList<>(buckets[i]);
            Collections.sort(bucketList);
            buckets[i].clear();
            buckets[i].addAll(bucketList);
        }
        // Merge buckets back into the original array.
        int index = 0;
        for (Queue<Double> bucket : buckets) {
            while (!bucket.isEmpty()) {
                arr[index++] = bucket.poll();
            }
        }
    }

    // ================================================================================
    // 4. iterativeQuickSortUsingStack: using java.util.Stack
    // ================================================================================
    /**
     * Sorts an array of integers using an iterative QuickSort algorithm.
     * This method uses a {@code Stack<int[]>} to simulate recursion.
     *
     * @param arr the array of integers to be sorted.
     */
    public static void iterativeQuickSortUsingStack(int[] arr) {
        if (arr == null || arr.length < 2) return;
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[] {0, arr.length - 1});
        while (!stack.isEmpty()) {
            int[] indices = stack.pop();
            int low = indices[0];
            int high = indices[1];
            if (low < high) {
                int pivotIndex = partition(arr, low, high);
                stack.push(new int[] {low, pivotIndex - 1});
                stack.push(new int[] {pivotIndex + 1, high});
            }
        }
    }

    /**
     * Partitions the array segment between indices {@code low} and {@code high} around a pivot.
     *
     * @param arr  the array to partition.
     * @param low  the starting index.
     * @param high the ending index.
     * @return the index of the pivot element after partitioning.
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

    /**
     * Swaps two elements in an array.
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

    // ================================================================================
    // Main method demonstrating usage of the sorting algorithms.
    // ================================================================================
    public static void main(String[] args) {
        // Demonstrate mergeSortedArraysGeneric:
        Integer[] left = {1, 3, 5};
        Integer[] right = {2, 4, 6};
        Integer[] merged = mergeSortedArraysGeneric(left, right);
        log.info("Merged Array (Generic): {}", Arrays.toString(merged));

        // Demonstrate insertionSortLinkedList:
        LinkedList<Integer> linkedList = new LinkedList<>(Arrays.asList(5, 3, 8, 1, 2));
        log.info("Original LinkedList: {}", linkedList);
        insertionSortLinkedList(linkedList);
        log.info("Sorted LinkedList: {}", linkedList);

        // Demonstrate bucketSortUsingQueue:
        double[] bucketArr = {0.78, 0.17, 0.39, 0.26, 0.72, 0.94, 0.21, 0.12, 0.23, 0.68};
        log.info("Original Bucket Array: {}", Arrays.toString(bucketArr));
        bucketSortUsingQueue(bucketArr);
        log.info("Sorted Bucket Array: {}", Arrays.toString(bucketArr));

        // Demonstrate iterativeQuickSortUsingStack:
        int[] quickSortArr = {29, 10, 14, 37, 13, 3, 7, 20};
        log.info("Original QuickSort Array: {}", Arrays.toString(quickSortArr));
        iterativeQuickSortUsingStack(quickSortArr);
        log.info("Sorted QuickSort Array: {}", Arrays.toString(quickSortArr));
    }

}
