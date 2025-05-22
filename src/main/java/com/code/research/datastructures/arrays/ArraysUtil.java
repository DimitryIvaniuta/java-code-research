package com.code.research.datastructures.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class ArraysUtil<E> {

    private E[] array;

    public ArraysUtil() {
        this.array = (E[]) new Object[5];
    }

    private int[] reverse(int a[]) {
        for (int i = 0, j = a.length - 1; i < j; i++, j--) {
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
        return a;
    }

    private int[] resize(int[] a) {
        return Arrays.copyOf(a, a.length * 2);
    }

    private void add(E element) {
        this.array = Arrays.copyOf(this.array, this.array.length + 1);
        this.array[this.array.length - 1] = element;
    }

    private E get(int index) {
        if(index < 0 || index >= this.array.length) {
            throw new IndexOutOfBoundsException();
        }
        return this.array[index];
    }

    public <T> T firstUnique(T[] array) {
        Map<T, Integer> counts = new LinkedHashMap<>();
        for (T e : array) {
            counts.put(e, counts.getOrDefault(e, 0) + 1);
        }
        for (Map.Entry<T, Integer> en : counts.entrySet()) {
            if (en.getValue() == 1) {
                return en.getKey();
            }
        }
        return null;
    }

    /**
     * Merges two sorted arrays into the first array in-place.
     *
     * @param a the first array, which has enough space at the end to hold all elements (length = m + n)
     * @param m the number of initialized elements in `a`
     * @param b the second array, with `n` initialized elements
     * @param n the number of elements in `b`
     */
    public void merge(int[] a, int m, int[] b, int n) {
        // i → last initialized element in a
        int i = m - 1;
        // j → last element in b
        int j = n - 1;
        // k → last position in merged array (a has total length m + n)
        int k = m + n - 1;

        // While there are still elements to merge from b...
        while (j >= 0) {
            // If there are still elements in a (i >= 0) AND
            // the current a[i] is greater than b[j], copy a[i] to a[k]
            if (i >= 0 && a[i] > b[j]) {
                a[k] = a[i];
                i--;    // move the pointer in a leftwards
            } else {
                // Otherwise, copy b[j] into a[k]
                a[k] = b[j];
                j--;    // move the pointer in b leftwards
            }
            k--; // move the destination pointer leftwards
        }
    }

    public List<ArraysApp.Person> sortByFullName(List<ArraysApp.Person> list) {
        list.sort(
                Comparator.<ArraysApp.Person,String>comparing(person -> person.lastName())
                        .thenComparing(person -> person.firstName())
        );
        return list;
    }

    public <T> List<T> removeDuplicates(List<T> list){
        return new ArrayList<>(new LinkedHashSet<>(list));
    }


}
