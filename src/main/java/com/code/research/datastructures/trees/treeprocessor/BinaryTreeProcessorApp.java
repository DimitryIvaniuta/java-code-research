package com.code.research.datastructures.trees.treeprocessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BinaryTreeProcessorApp {
    
    public static void main(String[] args) {
        // Example with Long:
        BinaryTreeProcessor<Long> longProcessor = new BinaryTreeProcessor<>();
        TreeNode<Long> longTree = longProcessor.constructTree(l -> l);
        log.info("Long Tree Root: {}", longTree.data);

        // Example with Integer:
        BinaryTreeProcessor<Integer> intProcessor = new BinaryTreeProcessor<>();
        TreeNode<Integer> intTree = intProcessor.constructTree(l -> (int) l);
        log.info("Integer Tree Root: {}", intTree.data);

        // Example with Float:
        BinaryTreeProcessor<Float> floatProcessor = new BinaryTreeProcessor<>();
        TreeNode<Float> floatTree = floatProcessor.constructTree(l -> (float) l);
        log.info("Float Tree Root: {}", floatTree.data);

        // Example with Double:
        BinaryTreeProcessor<Double> doubleProcessor = new BinaryTreeProcessor<>();
        TreeNode<Double> doubleTree = doubleProcessor.constructTree(l -> (double) l);
        log.info("Double Tree Root: {}", doubleTree.data);

        log.info("Pre-order Traversal: ");
        intProcessor.preOrder(intTree);
        log.info("");

        log.info("In-order Traversal: ");
        intProcessor.inOrder(intTree);
        log.info("");

        log.info("Post-order Traversal: ");
        intProcessor.postOrder(intTree);
        log.info("");

        log.info("Level-order Traversal: ");
        intProcessor.levelOrder(intTree);
        log.info("");

        log.info("Height of Tree: {}", intProcessor.height(intTree));
        log.info("Is Tree Balanced? {}", intProcessor.isBalanced(intTree));

        // Demonstrate mirror. (Cloning may be required to preserve original tree.)
        TreeNode<Integer> mirrored = intProcessor.mirror(intTree);
        log.info("Pre-order Traversal of Mirrored Tree: ");
        intProcessor.preOrder(mirrored);
        log.info("");

        // Search for a node with value 4.
        TreeNode<Integer> found = intProcessor.search(intTree, 4);
        log.info("Search for node with value 4: {}", (found != null ? found.data : "Not Found"));

        // Demonstrate Lowest Common Ancestor: find LCA for nodes 2 and 5.
        TreeNode<Integer> p = intProcessor.search(intTree, 2);
        TreeNode<Integer> q = intProcessor.search(intTree, 5);
        TreeNode<Integer> lca = intProcessor.lowestCommonAncestor(intTree, p, q);
        log.info("Lowest Common Ancestor of 2 and 5: {}", (lca != null ? lca.data : "None"));

        // Demonstrate iterative in-order traversal.
        log.info("Iterative In-order Traversal: ");
        intProcessor.inOrderIterative(intTree);
        log.info("");

        // Demonstrate level order traversal as list.
        log.info("Level Order Traversal (as list): {}", intProcessor.levelOrderList(intTree));
    }

}
