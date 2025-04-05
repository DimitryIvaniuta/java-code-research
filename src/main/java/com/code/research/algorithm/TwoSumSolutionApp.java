package com.code.research.algorithm;

public class TwoSumSolutionApp {

    /**
     * Main method demonstrating the usage of the twoSum method.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        TwoSumSolution solver = new TwoSumSolution();

        // Example 1
        int[] nums1 = {2, 7, 11, 15};
        int target1 = 9;
        int[] result1 = solver.twoSum(nums1, target1);
        System.out.println("Example 1 -> Indices: [" + result1[0] + ", " + result1[1] + "]");

        // Example 2
        int[] nums2 = {3, 2, 4};
        int target2 = 6;
        int[] result2 = solver.twoSum(nums2, target2);
        System.out.println("Example 2 -> Indices: [" + result2[0] + ", " + result2[1] + "]");
    }

}
