package com.code.research.datastructures.algorithm.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * SubsetGenerator provides a recursive algorithm to generate the power set (all possible subsets)
 * of a given list of elements.
 *
 * <p>This recursive solution uses backtracking to explore all inclusion/exclusion choices for each element.
 * It is widely used in feature selection, combinatorial optimization, and various data analysis applications.
 *
 * @param <T> the type of elements in the input list.
 */
public class SubsetGenerator<T> {

    /**
     * Generates all subsets (the power set) of the given input list.
     *
     * @param input the list of elements for which to generate subsets.
     * @return a list of lists, where each inner list is one possible subset of the input.
     */
    public List<List<T>> generateSubsets(List<T> input) {
        List<List<T>> subsets = new ArrayList<>();
        generateSubsetsRecursive(input, 0, new ArrayList<>(), subsets);
        return subsets;
    }

    /**
     * A recursive helper method that generates subsets using backtracking.
     *
     * @param input         the original list of elements.
     * @param index         the current index in the input list.
     * @param currentSubset the subset being constructed.
     * @param subsets       the collection of all subsets generated so far.
     */
    private void generateSubsetsRecursive(List<T> input, int index, List<T> currentSubset, List<List<T>> subsets) {
        // Base case: If we've reached the end of the input list, add a copy of the current subset.
        if (index == input.size()) {
            subsets.add(new ArrayList<>(currentSubset));
            return;
        }
        // Decision 1: Include the element at the current index.
        currentSubset.add(input.get(index));
        generateSubsetsRecursive(input, index + 1, currentSubset, subsets);

        // Decision 2: Exclude the element at the current index.
        currentSubset.removeLast();
        generateSubsetsRecursive(input, index + 1, currentSubset, subsets);
    }

}
