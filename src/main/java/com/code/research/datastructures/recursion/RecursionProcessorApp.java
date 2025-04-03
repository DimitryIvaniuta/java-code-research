package com.code.research.datastructures.recursion;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class RecursionProcessorApp {


    // =========================================================================
    // Main Method to Demonstrate Usage
    // =========================================================================
    /**
     * Main method demonstrating the usage of various recursive algorithms.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Factorial demonstration:
        log.info("Factorial (5) using simple recursion: {}", RecursionProcessor.factorial(5));
        log.info("Factorial (5) using tail recursion: {}", RecursionProcessor.factorialTail(5));

        // Fibonacci demonstration:
        log.info("Fibonacci (10) using naive recursion: {}", RecursionProcessor.fibonacci(10));
        log.info("Fibonacci (10) using memoization: {}", RecursionProcessor.fibonacciMemo(10));

        // Power set demonstration:
        List<Integer> set = Arrays.asList(1, 2, 3);
        List<List<Integer>> powerSetResult = RecursionProcessor.powerSet(set);
        log.info("Power set of " + set + ": {}", powerSetResult);

        // Tower of Hanoi demonstration:
        log.info("Tower of Hanoi (3 disks):");
        RecursionProcessor.solveHanoi(3, 'A', 'C', 'B');

        // Binary tree pre-order traversal demonstration:
        // Constructing a sample binary tree:
        //         1
        //       /   \
        //      2     3
        //     / \
        //    4   5
        TreeNode<Integer> root = new TreeNode<>(1);
        root.left = new TreeNode<>(2);
        root.right = new TreeNode<>(3);
        root.left.left = new TreeNode<>(4);
        root.left.right = new TreeNode<>(5);
        List<Integer> preOrder = RecursionProcessor.preOrderTraversal(root);
        log.info("Pre-order Traversal of Binary Tree: {}", preOrder);
    }
    
}
