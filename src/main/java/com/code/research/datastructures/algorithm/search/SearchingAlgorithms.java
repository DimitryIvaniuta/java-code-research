package com.code.research.datastructures.algorithm.search;

/**
 * SearchingAlgorithms provides static methods for performing common search operations
 * on arrays using both linear and binary search techniques.
 *
 * <p>These methods can be applied in many real-world scenarios, such as searching for
 * a record in a dataset, performing lookups in sorted tables, or finding a specific item
 * in a collection.
 *
 * @author Dzmitry Ivaniuta.
 */
public class SearchingAlgorithms {

    private SearchingAlgorithms() {
        //
    }

    /**
     * Performs a linear search for the target element in the given array.
     *
     * @param array  the array to search
     * @param target the element to search for
     * @param <T>    the type of elements in the array
     * @return the index of the target element if found; otherwise, -1
     */
    public static <T> int linearSearch(T[] array, T target) {
        if (array == null || target == null) {
            return -1;
        }
        for (int i = 0; i < array.length; i++) {
            if (target.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Performs a binary search for the target element in the given sorted array.
     * The array must be sorted in ascending order.
     *
     * @param array  the sorted array to search
     * @param target the element to search for
     * @param <T>    the type of elements in the array; must implement Comparable
     * @return the index of the target element if found; otherwise, -1
     */
    public static <T extends Comparable<? super T>> int binarySearch(T[] array, T target) {
        if (array == null || target == null) {
            return -1;
        }
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            T midVal = array[mid];
            int cmp = midVal.compareTo(target);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

}
