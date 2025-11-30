package com.code.research.algorithm;

public class MedianSortedArray {

    /**
     * Finds the median of two sorted arrays in O(log(min(m, n))) time.
     *
     * @param nums1 first sorted array
     * @param nums2 second sorted array
     * @return the median value
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure nums1 is the smaller array to binary search on it
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;
        int halfLen = (m + n + 1) / 2;

        int low = 0, high = m;
        while (low <= high) {
            int i = (low + high) / 2;       // partition in nums1
            int j = halfLen - i;           // corresponding partition in nums2

            // Get border elements around the partitions (use INT_MIN/INT_MAX for empty sides)
            int nums1LeftMax  = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int nums1RightMin = (i == m) ? Integer.MAX_VALUE : nums1[i];
            int nums2LeftMax  = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int nums2RightMin = (j == n) ? Integer.MAX_VALUE : nums2[j];

            // Check if partitions are correct
            if (nums1LeftMax <= nums2RightMin && nums2LeftMax <= nums1RightMin) {
                // Found correct partition
                if ((m + n) % 2 == 0) {
                    int leftMax  = Math.max(nums1LeftMax, nums2LeftMax);
                    int rightMin = Math.min(nums1RightMin, nums2RightMin);
                    return (leftMax + rightMin) / 2.0;
                } else {
                    return Math.max(nums1LeftMax, nums2LeftMax);
                }
            }
            // If left side of nums1 is too big, move partition i left
            else if (nums1LeftMax > nums2RightMin) {
                high = i - 1;
            }
            // Otherwise move partition i right
            else {
                low = i + 1;
            }
        }

        // Should never reach here if input arrays are valid
        throw new IllegalArgumentException("Input arrays are not sorted or have invalid lengths.");
    }

    public static void main(String[] args) {

        int[][][] testPairs = {
                {{1, 3}, {2}},           // odd total length → median = 2.0
                {{1, 2}, {3, 4}},        // even total length → median = (2+3)/2 = 2.5
                {{0, 0}, {0, 0}},        // identical → median = 0.0
                {{}, {1}},               // one empty array → median = 1.0
                {{2}, {}}                // other empty array → median = 2.0
        };

        for (int[][] pair : testPairs) {
            int[] a = pair[0], b = pair[1];
            double median = findMedianSortedArrays(a, b);
            System.out.printf("nums1 = %s, nums2 = %s → median = %.1f%n",
                    java.util.Arrays.toString(a),
                    java.util.Arrays.toString(b),
                    median);
        }
    }

}
