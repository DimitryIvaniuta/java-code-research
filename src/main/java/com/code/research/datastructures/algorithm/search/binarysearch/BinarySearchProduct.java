package com.code.research.datastructures.algorithm.search.binarysearch;

public class BinarySearchProduct {

    private BinarySearchProduct() {
        //
    }

    /**
     * Performs a binary search for a product with the specified target id in the sorted array.
     *
     * @param products the sorted array of products (sorted by product id in ascending order)
     * @param targetId the product id to search for
     * @return the Product if found; otherwise, null
     */
    public static Product binarySearch(Product[] products, int targetId) {
        int left = 0;
        int right = products.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midId = products[mid].getId();
            if (midId == targetId) {
                return products[mid];
            } else if (midId < targetId) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }
}
