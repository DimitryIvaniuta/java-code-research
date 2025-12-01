package com.code.research.algorithm;

public class BinarySearch704 {

    // Return index of target in sorted nums, or -1 if not found (O(log n))
    public static int search(int[] nums, int target) {
        // search range [l..r]
        int l = 0;
        int r = nums.length - 1;
        while (l <= r) {
            // mid (avoids overflow)
            int m = (r + l) >>> 1;
            // found
            if (nums[m] == target) {
                return m;
            }
            // target is on the right
            if (nums[m] < target) {
                l = m + 1;
            } else {
                // target is on the left
                r = m - 1;
            }
        }
        return -1;                                   // not present
    }

    public static void main(String[] args) {
        System.out.println(search(new int[]{-1, 0, 3, 5, 9, 12}, 9)); // 4
        System.out.println(search(new int[]{-1, 0, 3, 5, 9, 12}, 3)); // 2
        System.out.println(search(new int[]{-1, 0, 3, 5, 9, 12}, 2)); // -1
    }
}
