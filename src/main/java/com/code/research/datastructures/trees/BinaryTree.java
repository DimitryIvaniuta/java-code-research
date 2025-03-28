package com.code.research.datastructures.trees;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * BinaryTree represents a simple binary tree with various traversal methods.
 *
 * @param <T> the type of data stored in the tree nodes.
 */
@Slf4j
public class BinaryTree<T> {

    /**
     * Represents a node in the binary tree.
     *
     * @param <T> the type of data stored in the node.
     */
    public static class Node<T> {
        /** The data stored in the node. */
        public T data;
        /** Reference to the left child node. */
        public Node<T> left;
        /** Reference to the right child node. */
        public Node<T> right;

        /**
         * Constructs a new node with the given data.
         *
         * @param data the data to store in the node.
         */
        public Node(T data) {
            this.data = data;
        }
    }

    /** The root node of the binary tree. */
    private Node<T> root;

    /**
     * Constructs an empty BinaryTree.
     */
    public BinaryTree() {
        // No-args constructor
    }

    /**
     * Constructs a BinaryTree with the given root node.
     *
     * @param root the root node of the tree.
     */
    public BinaryTree(Node<T> root) {
        this.root = root;
    }

    /**
     * Returns the root node of the tree.
     *
     * @return the root node.
     */
    public Node<T> getRoot() {
        return root;
    }

    /**
     * Sets the root node of the tree.
     *
     * @param root the node to set as root.
     */
    public void setRoot(Node<T> root) {
        this.root = root;
    }

    /**
     * Performs a pre-order traversal of the tree and prints node data.
     *
     * @param node the starting node.
     */
    public void preOrderTraversal(Node<T> node) {
        if (node == null) {
            return;
        }
        log.info("{} ", node.data);
        preOrderTraversal(node.left);
        preOrderTraversal(node.right);
    }

    /**
     * Performs an in-order traversal of the tree and prints node data.
     *
     * @param node the starting node.
     */
    public void inOrderTraversal(Node<T> node) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left);
        log.info("{} ", node.data);
        inOrderTraversal(node.right);
    }

    /**
     * Performs a post-order traversal of the tree and prints node data.
     *
     * @param node the starting node.
     */
    public void postOrderTraversal(Node<T> node) {
        if (node == null) {
            return;
        }
        postOrderTraversal(node.left);
        postOrderTraversal(node.right);
        log.info("{} ", node.data);
    }

    /**
     * Performs a level-order (breadth-first) traversal of the tree and prints node data.
     */
    public void levelOrderTraversal() {
        if (root == null) {
            return;
        }
        Queue<Node<T>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<T> current = queue.poll();
            log.info("{} ", current.data);
            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }
    }

}
