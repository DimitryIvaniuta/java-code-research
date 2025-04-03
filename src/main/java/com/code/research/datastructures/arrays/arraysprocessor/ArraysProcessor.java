package com.code.research.datastructures.arrays.arraysprocessor;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.*;

/**
 * {@code ArraysProcessor} is a utility class that provides a collection of static methods for processing arrays.
 *
 * <p>This class includes methods for:
 * <ul>
 *   <li>Finding the minimum and maximum element in an array.</li>
 *   <li>Reversing an array in place.</li>
 *   <li>Performing binary search (iteratively and recursively) on sorted arrays.</li>
 *   <li>Merging two sorted arrays into a single sorted array.</li>
 *   <li>Removing duplicates from a sorted array.</li>
 *   <li>Computing a frequency map of elements in an array.</li>
 *   <li>Summing the elements of an int array.</li>
 * </ul>
 *
 * <p>This class demonstrates common array manipulations used in real-world applications, such as data processing,
 * search algorithms, and statistics.
 */
@Slf4j
public class ArraysProcessor {

    private ArraysProcessor() {
        //
    }

    /**
     * Finds and returns the minimum element in the given array.
     *
     * @param array the array of elements.
     * @param <T>   the type of elements that extend Comparable.
     * @return the minimum element in the array.
     * @throws IllegalArgumentException if the array is null or empty.
     */
    public static <T extends Comparable<? super T>> T findMin(T[] array) {
        if (array == null || array.length == 0)
            throw new IllegalArgumentException("Array is empty");
        T min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(min) < 0) {
                min = array[i];
            }
        }
        return min;
    }

    /**
     * Finds and returns the maximum element in the given array.
     *
     * @param array the array of elements.
     * @param <T>   the type of elements that extend Comparable.
     * @return the maximum element in the array.
     * @throws IllegalArgumentException if the array is null or empty.
     */
    public static <T extends Comparable<? super T>> T findMax(T[] array) {
        if (array == null || array.length == 0)
            throw new IllegalArgumentException("Array is empty");
        T max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(max) > 0) {
                max = array[i];
            }
        }
        return max;
    }

    /**
     * Reverses the given array in place.
     *
     * @param array the array to reverse.
     * @param <T>   the type of elements in the array.
     */
    public static <T> void reverse(T[] array) {
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            T temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }
    }

    /**
     * Performs an iterative binary search on a sorted array.
     *
     * @param array the sorted array of elements.
     * @param key   the key to search for.
     * @param <T>   the type of elements that extend Comparable.
     * @return the index of the key if found; otherwise, -1.
     */
    public static <T extends Comparable<? super T>> int binarySearchIterative(T[] array, T key) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int cmp = array[mid].compareTo(key);
            if (cmp == 0) return mid;
            else if (cmp < 0) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }

    /**
     * Performs a recursive binary search on a sorted array.
     *
     * @param array the sorted array of elements.
     * @param key   the key to search for.
     * @param left  the left index of the search range.
     * @param right the right index of the search range.
     * @param <T>   the type of elements that extend Comparable.
     * @return the index of the key if found; otherwise, -1.
     */
    public static <T extends Comparable<? super T>> int binarySearchRecursive(T[] array, T key, int left, int right) {
        if (left > right) return -1;
        int mid = left + (right - left) / 2;
        int cmp = array[mid].compareTo(key);
        if (cmp == 0) return mid;
        else if (cmp < 0) return binarySearchRecursive(array, key, mid + 1, right);
        else return binarySearchRecursive(array, key, left, mid - 1);
    }

    /**
     * Merges two sorted arrays into a single sorted array.
     *
     * @param a   the first sorted array.
     * @param b   the second sorted array.
     * @param <T> the type of elements that extend Comparable.
     * @return a new array containing all elements from a and b in sorted order.
     */
    public static <T extends Comparable<? super T>> T[] mergeSortedArrays(T[] a, T[] b) {
        int totalLength = a.length + b.length;
        @SuppressWarnings("unchecked")
        T[] merged = (T[]) Array.newInstance(a.getClass().getComponentType(), totalLength);
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < a.length && j < b.length) {
            merged[k++] = (a[i].compareTo(b[j]) <= 0) ? a[i++] : b[j++];
        }
        while (i < a.length) merged[k++] = a[i++];
        while (j < b.length) merged[k++] = b[j++];
        return merged;
    }

    /**
     * Removes duplicates from a sorted array in place.
     *
     * @param array the sorted array from which duplicates should be removed.
     * @param <T>   the type of elements.
     * @return the new length of the array after removing duplicates.
     */
    public static <T> int removeDuplicates(T[] array) {
        if (array.length == 0) {
            return 0;
        }
        int j = 0;
        for (int i = 1; i < array.length; i++) {
            if (!array[i].equals(array[j])) {
                array[++j] = array[i];
            }
        }
        return j + 1;
    }

    /**
     * Computes a frequency map for the given array.
     *
     * @param array the array of elements.
     * @param <T>   the type of elements.
     * @return a map where each key is an element from the array and the value is the number of occurrences.
     */
    public static <T> Map<T, Integer> frequencyCount(T[] array) {
        Map<T, Integer> freqMap = new HashMap<>();
        for (T element : array) {
            freqMap.put(element, freqMap.getOrDefault(element, 0) + 1);
        }
        return freqMap;
    }

    /**
     * Computes the sum of all elements in an int array.
     *
     * @param array the int array.
     * @return the sum of the array's elements.
     */
    public static int sum(int[] array) {
        int total = 0;
        for (int val : array) {
            total += val;
        }
        return total;
    }

}
