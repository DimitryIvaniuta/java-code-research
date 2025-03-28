package com.code.research.datastructures.trees;


import lombok.extern.slf4j.Slf4j;

/**
 * BinarySearchTree implements a binary search tree (BST) supporting insertion, search,
 * and in-order traversal. The generic type T must implement Comparable.
 *
 * @param <T> the type of elements stored in the BST.
 */
@Slf4j
public class BinarySearchTree<T extends Comparable<T>> {


    /** The root node of the BST. */
    private Node<T> root;

    /**
     * Inserts the specified value into the BST.
     *
     * @param value the value to insert.
     */
    public void insert(T value) {
        root = insertRec(root, value);
    }

    private Node<T> insertRec(Node<T> node, T value) {
        if (node == null) {
            return new Node<>(value);
        }
        if (value.compareTo(node.getData()) < 0) {
            node.setLeft(insertRec(node.getLeft(), value));
        } else if (value.compareTo(node.getData()) > 0) {
            node.setRight(insertRec(node.getRight(), value));
        }
        return node;
    }

    /**
     * Searches for the specified value in the BST.
     *
     * @param value the value to search for.
     * @return true if found, false otherwise.
     */
    public boolean search(T value) {
        return searchRec(root, value);
    }

    private boolean searchRec(Node<T> node, T value) {
        if (node == null) {
            return false;
        }
        if (value.equals(node.getData())) {
            return true;
        }
        return value.compareTo(node.getData()) < 0 ? searchRec(node.getLeft(), value) : searchRec(node.getRight(), value);
    }

    /**
     * Performs an in-order traversal of the BST and prints node data.
     */
    public void inOrderTraversal() {
        inOrderRec(root);
    }

    private void inOrderRec(Node<T> node) {
        if (node == null) {
            return;
        }
        inOrderRec(node.getLeft());
        log.info("{} ", node.getData());
        inOrderRec(node.getRight());
    }

}
