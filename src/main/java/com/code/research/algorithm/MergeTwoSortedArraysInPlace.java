package com.code.research.algorithm;

public final class MergeTwoSortedArraysInPlace {

    /**
     * Merge two sorted arrays in-place so that:
     *  - a[] contains the smallest a.length elements (sorted)
     *  - b[] contains the remaining elements (sorted)
     * Uses no extra arrays. Very readable; O(n * m) worst-case.
     */
    public static void mergeSimple(int[] a, int[] b) {
        int n = a.length;
        int m = b.length;
        for (int i = 0; i < n; i++) {
            // If current a[i] is bigger than the smallest of b,
            // swap and then put that b[0] at its correct spot in b (insertion).
            if (m > 0 && a[i] > b[0]) {
                int tmp = a[i];
                a[i] = b[0];
                b[0] = tmp;

                // Insert b[0] into sorted position within b[1..m-1]
                // (standard insertion-sort single step)
                int first = b[0];
                int j = 1;
                while (j < m && b[j] < first) {
                    b[j - 1] = b[j];
                    j++;
                }
                b[j - 1] = first;
            }
        }
    }

    // Rearranges so that a[] has the smallest n elements (sorted) and b[] has the rest (sorted).
    public static void merge(int[] a, int[] b) {
        int n = a.length;
        int m = b.length;
        for (int gap = nextGap(n + m); gap > 0; gap = nextGap(gap)) {

            // 1) Compare inside a[]
            for (int i = 0; i + gap < n; i++) {
                int j = i + gap;
                if (a[i] > a[j]) swap(a, i, j);
            }

            // 2) Compare across a[] and b[]
            // i in a, j in b such that i+gap may land in b
            int i = Math.max(0, n - gap);           // earliest i that pairs with b[0]
            int j = gap > n ? gap - n : 0;          // starting j in b
            while (i < n && j < m) {
                if (a[i] > b[j]) swap(a, i, b, j);
                i++; j++;
            }

            // 3) Compare inside b[]
            for (int p = 0; p + gap < m; p++) {
                int q = p + gap;
                if (b[p] > b[q]) swap(b, p, q);
            }
        }
    }

    // Next gap like Shell sort: ceil(g/2) until it reaches 0
    private static int nextGap(int g) { return (g <= 1) ? 0 : (g / 2 + g % 2); }

    private static void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }
    private static void swap(int[] a, int i, int[] b, int j) { int t = a[i]; a[i] = b[j]; b[j] = t; }


    // tiny demo
    public static void main(String[] args) {
        int[] a1 = {2,4,7,10}, b1 = {2,3};
        merge(a1, b1);
        System.out.println(java.util.Arrays.toString(a1)); // [2, 2, 3, 4]
        System.out.println(java.util.Arrays.toString(b1)); // [7, 10]

        int[] a2 = {1,5,9,10,15,20}, b2 = {2,3,8,13};
        merge(a2, b2);
        System.out.println(java.util.Arrays.toString(a2)); // [1, 2, 3, 5, 8, 9]
        System.out.println(java.util.Arrays.toString(b2)); // [10, 13, 15, 20]

        int[] a3 = {0,1}, b3 = {2,3};
        merge(a3, b3);
        System.out.println(java.util.Arrays.toString(a3)); // [0, 1]
        System.out.println(java.util.Arrays.toString(b3)); // [2, 3]

        int[] a = {1, 5, 9, 10, 15, 20};
        int[] b = {2, 3, 8, 13};
        mergeSimple(a, b);
        System.out.println("Simple algorithm:");
        System.out.println(java.util.Arrays.toString(a)); // [1, 2, 3, 5, 8, 9]
        System.out.println(java.util.Arrays.toString(b)); // [10, 13, 15, 20]

    }
}
