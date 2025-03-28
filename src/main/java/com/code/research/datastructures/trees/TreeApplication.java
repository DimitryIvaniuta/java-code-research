package com.code.research.datastructures.trees;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TreeApplication {

    public static void main(String[] args) {
        // Demonstration: Binary Tree traversals.
        log.info("=== Binary Tree Traversals ===");
        BinaryTree<Integer> binaryTree = new BinaryTree<>();
        BinaryTree.Node<Integer> btRoot = new BinaryTree.Node<>(1);
        btRoot.left = new BinaryTree.Node<>(2);
        btRoot.right = new BinaryTree.Node<>(3);
        btRoot.left.left = new BinaryTree.Node<>(4);
        btRoot.left.right = new BinaryTree.Node<>(5);
        binaryTree.setRoot(btRoot);
        log.info("Pre-Order: ");
        binaryTree.preOrderTraversal(binaryTree.getRoot());
        log.info("In-Order: ");
        binaryTree.inOrderTraversal(binaryTree.getRoot());
        log.info("Post-Order: ");
        binaryTree.postOrderTraversal(binaryTree.getRoot());
        log.info("Level-Order: ");
        binaryTree.levelOrderTraversal();
        log.info("\n");

        // Demonstration: Binary Search Tree.
        log.info("=== Binary Search Tree (BST) ===");
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(50);
        bst.insert(30);
        bst.insert(20);
        bst.insert(40);
        bst.insert(70);
        bst.insert(60);
        bst.insert(80);
        log.info("In-Order Traversal: ");
        bst.inOrderTraversal();
        log.info("Search 40: {}", bst.search(40));
        log.info("Search 100: {}", bst.search(100));


        // Demonstration: Trie.
        log.info("=== Trie (Prefix Tree) ===");
        Trie trie = new Trie();
        trie.insert("hello");
        trie.insert("world");
        trie.insert("hi");
        trie.insert("her");
        trie.insert("hero");
        log.info("Search 'hello': " + trie.search("hello"));
        log.info("Search 'her': " + trie.search("her"));
        log.info("Search 'hero': " + trie.search("hero"));
        log.info("Starts with 'he': {}", trie.startsWith("he"));
        log.info("Starts with 'wo': {}", trie.startsWith("wo"));
        log.info("Starts with 'ha': {}", trie.startsWith("ha"));
    }

}
