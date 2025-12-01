package com.code.research.algorithm;

public class BinarySearchString {

    private static int search(String[] list, String key) {
        int low = 0;
        int high = list.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            String midVal = list[mid];
            int cmp = midVal.compareTo(key);
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

    public static void main(String[] args) {
        String[] names = new String[]{"Alice", "Bob", "Carol", "Dave", "James"};
        System.out.println(search(names, "Carol")); // 2
        System.out.println(search(names, "Eve"));   // -1
    }

}
