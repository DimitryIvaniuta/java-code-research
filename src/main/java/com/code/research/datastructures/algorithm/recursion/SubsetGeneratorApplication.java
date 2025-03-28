package com.code.research.datastructures.algorithm.recursion;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SubsetGeneratorApplication {

    /**
     * Main method demonstrating the usage of the SubsetGenerator.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Create a sample input list.
        List<Integer> input = List.of(1, 2, 3, 4);

        // Generate all subsets.
        SubsetGenerator<Integer> generator = new SubsetGenerator<>();
        List<List<Integer>> allSubsets = generator.generateSubsets(input);

        // Print the results.
        log.info("Subsets of {}: ", input);
        for (List<Integer> subset : allSubsets) {
            log.info("{}", subset);
        }
    }

}
