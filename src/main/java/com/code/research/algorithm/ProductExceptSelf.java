package com.code.research.algorithm;

import java.util.Arrays;

public final class ProductExceptSelf {
    // Return array where ans[i] = product of all nums[j] (j != i), no division, O(n) time, O(1) extra (output excluded)
    public static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        // start with 1 so we can multiply prefixes/suffixes into place
        Arrays.fill(ans, 1);

        // prefix pass: ans[i] gets product of all elements to the left of i
        for (int i = 1; i < n; i++) {
            ans[i] = ans[i - 1] * nums[i - 1];
        }

        // suffix pass: multiply in product of all elements to the right of i
        // running suffix product
        int suf = 1;
        for (int i = n - 1; i >= 0; i--) {
            // combine prefix (already in ans[i]) with suffix
            ans[i] *= suf;
            // update suffix for next position to the left
            suf *= nums[i];
        }
        return ans;
    }

    // tiny demo
    public static void main(String[] args) {
        System.out.println(Arrays.toString(productExceptSelf(new int[]{1, 2, 3, 4})));      // [24,12,8,6]
        System.out.println(Arrays.toString(productExceptSelf(new int[]{-1, 1, 0, -3, 3})));  // [0,0,9,0,0]
    }
}
