package com.code.research.algorithm;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class BinarySearchUsingJava {
    // 1) On primitive int[] with Arrays.binarySearch (O(log n))
    public static int search(int[] nums, int target) {
        int pos = Arrays.binarySearch(nums, target);   // ≥0 → found index; <0 → not found
        return (pos >= 0) ? pos : -1;                  // normalize to -1 when absent
    }

    // 2) If you also want the insertion index (where target should be inserted):
    public static int searchInsert(int[] nums, int target) {
        int pos = Arrays.binarySearch(nums, target);
        return (pos >= 0) ? pos : -(pos + 1);         // Java’s convention: -(insertionPoint) - 1
    }

    // 3) On a sorted List<T> with Collections.binarySearch (uses compareTo / Comparator)
    public static <T extends Comparable<? super T>> int search(List<T> list, T key) {
        int pos = Collections.binarySearch(list, key);
        return (pos >= 0) ? pos : -1;
    }

    // tiny demo
    public static void main(String[] args) {
        int[] a = {-1, 0, 3, 5, 9, 12};
        System.out.println(search(a, 9));        // 4
        System.out.println(search(a, 2));        // -1
        System.out.println(searchInsert(a, 2));  // 2 (insert before 3)

        List<String> names = List.of("Alice", "James", "Bob", "Carol", "Dave");
        System.out.println(search(names, "Carol")); // 3
        System.out.println(search(names, "Eve"));   // -1
    }
}
