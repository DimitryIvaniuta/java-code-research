package com.code.research.algorithm;

public class BinarySearchArray {

    private static int binarySearch(int[] a, int fromIndex, int toIndex,
                                    int key) {
        int left = fromIndex;
        int right = toIndex - 1;

        while (left <= right) {
            int mid = (right + left) >>> 1;
            int midVal = a[mid];
            if (midVal < key) {
                left = mid + 1;
            } else if (midVal > key) {
                right = mid - 1;
            } else {
                return mid;
            }
        }

        return -(left + 1);
    }

    public static void main(String[] args) {
        int[] a = {-1, 0, 3, 5, 9, 12};
        System.out.println(binarySearch(a, 0, a.length, 9));  // 4
        System.out.println(binarySearch(a, 0, a.length, 2));  // -3
        System.out.println(binarySearch(a, 0, a.length, 5));  // 3
    }

}
