package com.code.research.interviewpatterns;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Clean Java templates for common interview patterns.
 * Copy-paste and adapt. Each method is O(1) extra boilerplate and focuses on the pattern.
 */
public final class Patterns {

    private Patterns() {
    }

    // ============================================================
    // 1) HashMap pattern: "lookup while iterating"
    // Example: Two Sum (return indices)
    // Time: O(n), Space: O(n)
    // ============================================================
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> indexByValue = new HashMap<>(); // value -> index

        for (int i = 0; i < nums.length; i++) {
            int need = target - nums[i];
            Integer j = indexByValue.get(need);
            if (j != null) {
                return new int[]{j, i};
            }
            indexByValue.put(nums[i], i);
        }
        return new int[]{-1, -1}; // not found
    }

    // ============================================================
    // 2) Two Pointers pattern (sorted array / opposite ends)
    // Example: Two Sum II on sorted array (1-based indices)
    // Time: O(n), Space: O(1)
    // ============================================================
    public static int[] twoSumSorted(int[] numbers, int target) {
        int l = 0, r = numbers.length - 1;

        while (l < r) {
            int sum = numbers[l] + numbers[r];
            if (sum == target) {
                return new int[]{l + 1, r + 1}; // 1-based
            } else if (sum < target) {
                l++;
            } else {
                r--;
            }
        }
        return new int[]{-1, -1};
    }

    // ============================================================
    // 3a) Sliding Window (fixed size K)
    // Example: Maximum sum of subarray size k
    // Time: O(n), Space: O(1)
    // ============================================================
    public static long maxSumSubarrayOfSizeK(int[] nums, int k) {
        if (k <= 0 || k > nums.length) return Long.MIN_VALUE;

        long windowSum = 0;
        for (int i = 0; i < k; i++) windowSum += nums[i];

        long best = windowSum;

        for (int r = k; r < nums.length; r++) {
            windowSum += nums[r];
            windowSum -= nums[r - k];
            if (windowSum > best) best = windowSum;
        }
        return best;
    }

    // ============================================================
    // 3b) Sliding Window (variable size)
    // Example: Min size subarray sum >= target (positive integers)
    // Time: O(n), Space: O(1)
    // ============================================================
    public static int minSubarrayLenAtLeastTarget(int target, int[] nums) {
        int n = nums.length;
        int best = Integer.MAX_VALUE;

        int left = 0;
        long sum = 0;

        for (int right = 0; right < n; right++) {
            sum += nums[right];

            while (sum >= target) {
                best = Math.min(best, right - left + 1);
                sum -= nums[left++];
            }
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }

    // ============================================================
    // 4) Kadane’s Algorithm (maximum subarray sum)
    // Time: O(n), Space: O(1)
    // ============================================================
    public static long maxSubarraySumKadane(int[] nums) {
        if (nums.length == 0) return 0;

        long cur = nums[0];
        long best = nums[0];

        for (int i = 1; i < nums.length; i++) {
            cur = Math.max(nums[i], cur + nums[i]);
            best = Math.max(best, cur);
        }
        return best;
    }

    // ============================================================
    // 5) Binary Search (classic) on sorted array
    // Returns index of target or -1.
    // Time: O(log n), Space: O(1)
    // ============================================================
    public static int binarySearch(int[] a, int target) {
        int l = 0, r = a.length - 1;

        while (l <= r) {
            int mid = l + ((r - l) >>> 1); // safe from overflow
            if (a[mid] == target) return mid;

            if (a[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return -1;
    }

    // Optional helper: lowerBound (first index i where a[i] >= target)
    public static int lowerBound(int[] a, int target) {
        int l = 0, r = a.length; // [l, r)
        while (l < r) {
            int mid = l + ((r - l) >>> 1);
            if (a[mid] < target) l = mid + 1;
            else r = mid;
        }
        return l;
    }

    // ============================================================
    // 6) Rotated Binary Search (Search in Rotated Sorted Array)
    // Array has unique values. Returns index or -1.
    // Time: O(log n), Space: O(1)
    // ============================================================
    public static int searchRotated(int[] a, int target) {
        int l = 0, r = a.length - 1;

        while (l <= r) {
            int mid = l + ((r - l) >>> 1);
            if (a[mid] == target) return mid;

            // Left half is sorted
            if (a[l] <= a[mid]) {
                // Target is in the sorted left half
                if (a[l] <= target && target < a[mid]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            // Right half is sorted
            else {
                // Target is in the sorted right half
                if (a[mid] < target && target <= a[r]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return -1;
    }

    // ------------------------------------------------------------
    // Tiny sanity checks (optional)
    // ------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum(new int[]{2, 7, 11, 15}, 9))); // [0,1]
        System.out.println(Arrays.toString(twoSumSorted(new int[]{2, 7, 11, 15}, 9))); // [1,2]
        System.out.println(maxSumSubarrayOfSizeK(new int[]{1, 2, 3, 4, 5}, 2)); // 9
        System.out.println(minSubarrayLenAtLeastTarget(7, new int[]{2, 3, 1, 2, 4, 3})); // 2
        System.out.println(maxSubarraySumKadane(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4})); // 6
        System.out.println(binarySearch(new int[]{1, 3, 5, 7, 9}, 7)); // 3
        System.out.println(lowerBound(new int[]{1, 3, 3, 5, 7}, 3)); // 1
        System.out.println(searchRotated(new int[]{4, 5, 6, 7, 0, 1, 2}, 0)); // 4
    }
}
