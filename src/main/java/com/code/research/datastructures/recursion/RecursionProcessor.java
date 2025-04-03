package com.code.research.datastructures.recursion;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RecursionProcessor provides a collection of recursive algorithms demonstrating 
 * various common recursive problems and techniques.
 *
 * <p>This class includes:
 * <ul>
 *   <li>Factorial calculation using both simple recursion and tail recursion.</li>
 *   <li>Fibonacci sequence calculation using naive recursion and memoization.</li>
 *   <li>Generation of the power set (all subsets) of a list of integers.</li>
 *   <li>Solution to the Tower of Hanoi problem, printing each move.</li>
 *   <li>Pre-order traversal of a binary tree, returning a list of node values.</li>
 * </ul>
 *
 * <p>Each method is designed to illustrate key recursive principles, base cases, and optimization
 * techniques such as tail recursion and memoization.
 */
@Slf4j
public class RecursionProcessor {

    private RecursionProcessor() {
        //
    }

    // =========================================================================
    // 1. Factorial (Simple Recursion)
    // =========================================================================
    /**
     * Computes the factorial of n using simple recursion.
     *
     * @param n the non-negative integer for which factorial is computed.
     * @return the factorial of n.
     */
    public static long factorial(int n) {
        if (n <= 1) return 1; // Base case
        return n * factorial(n - 1);
    }

    // =========================================================================
    // 2. Factorial (Tail Recursion)
    // =========================================================================
    /**
     * Computes the factorial of n using tail recursion.
     *
     * @param n the non-negative integer for which factorial is computed.
     * @return the factorial of n.
     */
    public static long factorialTail(int n) {
        return factorialTailHelper(n, 1);
    }

    /**
     * Helper method for tail-recursive factorial computation.
     *
     * @param n           the current number.
     * @param accumulator the accumulated factorial result.
     * @return the factorial of n.
     */
    private static long factorialTailHelper(int n, long accumulator) {
        if (n <= 1) return accumulator;
        return factorialTailHelper(n - 1, n * accumulator);
    }

    // =========================================================================
    // 3. Fibonacci (Naive Recursion)
    // =========================================================================
    /**
     * Computes the nth Fibonacci number using naive recursion.
     *
     * @param n the index of the Fibonacci number to compute.
     * @return the nth Fibonacci number.
     */
    public static int fibonacci(int n) {
        if (n <= 1) return n; // Base cases: fib(0)=0, fib(1)=1
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    // =========================================================================
    // 4. Fibonacci (Memoization)
    // =========================================================================
    /**
     * Computes the nth Fibonacci number using recursion with memoization.
     *
     * @param n the index of the Fibonacci number to compute.
     * @return the nth Fibonacci number.
     */
    public static int fibonacciMemo(int n) {
        Map<Integer, Integer> memo = new HashMap<>();
        return fibonacciMemoHelper(n, memo);
    }

    /**
     * Helper method for Fibonacci calculation with memoization.
     *
     * @param n    the Fibonacci index.
     * @param memo a cache for already computed Fibonacci numbers.
     * @return the nth Fibonacci number.
     */
    private static int fibonacciMemoHelper(int n, Map<Integer, Integer> memo) {
        if (n <= 1) return n;
        if (memo.containsKey(n)) return memo.get(n);
        int result = fibonacciMemoHelper(n - 1, memo) + fibonacciMemoHelper(n - 2, memo);
        memo.put(n, result);
        return result;
    }

    // =========================================================================
    // 5. Power Set Generation (All Subsets)
    // =========================================================================
    /**
     * Generates the power set (all subsets) of the input list of integers.
     *
     * @param set the list of integers.
     * @return a list of lists, where each inner list is a subset of the input.
     */
    public static List<List<Integer>> powerSet(List<Integer> set) {
        List<List<Integer>> result = new ArrayList<>();
        generatePowerSet(set, 0, new ArrayList<>(), result);
        return result;
    }

    /**
     * Recursive helper method to generate the power set.
     *
     * @param set     the input list of integers.
     * @param index   the current index in the list.
     * @param current the current subset being built.
     * @param result  the list of all subsets generated.
     */
    private static void generatePowerSet(List<Integer> set, int index, List<Integer> current, List<List<Integer>> result) {
        if (index == set.size()) {
            result.add(new ArrayList<>(current));
            return;
        }
        // Include current element.
        current.add(set.get(index));
        generatePowerSet(set, index + 1, current, result);
        // Exclude current element.
        current.removeLast();
        generatePowerSet(set, index + 1, current, result);
    }

    // =========================================================================
    // 6. Tower of Hanoi
    // =========================================================================
    /**
     * Solves the Tower of Hanoi problem.
     *
     * <p>The method moves n disks from the source peg to the destination peg using the auxiliary peg.
     * The parameters are ordered as: (n, source, auxiliary, destination) to match the standard algorithm.
     *
     * <p>Algorithm:
     * <ol>
     *   <li>If n == 1, move the disk directly from source to destination.</li>
     *   <li>Recursively move n-1 disks from source to auxiliary using destination as temporary.</li>
     *   <li>Move the nth (largest) disk from source to destination.</li>
     *   <li>Recursively move the n-1 disks from auxiliary to destination using source as temporary.</li>
     * </ol>
     *
     * @param n           the number of disks to move.
     * @param source      the source peg.
     * @param auxiliary   the auxiliary (helper) peg.
     * @param destination the destination peg.
     */
    public static void solveHanoi(int n, char source, char auxiliary, char destination) {
        if (n == 1) {
            log.info("Move disk 1 from {} to {}", source, destination);
            return;
        }
        // Move n-1 disks from source to auxiliary, using destination as temporary.
        solveHanoi(n - 1, source, destination, auxiliary);
        // Move the nth disk from source to destination.
        log.info("Move disk {} from {} to {}", n, source, destination);
        // Move the n-1 disks from auxiliary to destination, using source as temporary.
        solveHanoi(n - 1, auxiliary, source, destination);
    }

    // =========================================================================
    // 7. Binary Tree Pre-Order Traversal
    // =========================================================================

    /**
     * Performs a recursive pre-order traversal of a binary tree.
     * The traversal visits the root, then left subtree, and finally the right subtree.
     *
     * @param root the root of the binary tree.
     * @param <T>  the type of data in the tree nodes.
     * @return a list of node values in pre-order.
     */
    public static <T> List<T> preOrderTraversal(TreeNode<T> root) {
        List<T> result = new ArrayList<>();
        preOrderHelper(root, result);
        return result;
    }

    /**
     * Helper method for pre-order traversal.
     *
     * @param root   the current node.
     * @param result the list accumulating the node values.
     * @param <T>    the type of data in the tree nodes.
     */
    private static <T> void preOrderHelper(TreeNode<T> root, List<T> result) {
        if (root == null) return;
        result.add(root.data);
        preOrderHelper(root.left, result);
        preOrderHelper(root.right, result);
    }

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
        log.info("Factorial (5) using simple recursion: " + factorial(5));
        log.info("Factorial (5) using tail recursion: " + factorialTail(5));

        // Fibonacci demonstration:
        log.info("Fibonacci (10) using naive recursion: " + fibonacci(10));
        log.info("Fibonacci (10) using memoization: " + fibonacciMemo(10));

        // Power set demonstration:
        List<Integer> set = Arrays.asList(1, 2, 3);
        List<List<Integer>> powerSetResult = powerSet(set);
        log.info("Power set of " + set + ": " + powerSetResult);

        // Tower of Hanoi demonstration:
        log.info("Tower of Hanoi (3 disks):");
        solveHanoi(3, 'A', 'C', 'B');

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
        List<Integer> preOrder = preOrderTraversal(root);
        log.info("Pre-order Traversal of Binary Tree: " + preOrder);
    }
    
}
