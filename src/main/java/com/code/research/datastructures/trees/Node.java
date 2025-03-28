package com.code.research.datastructures.trees;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a node in the binary search tree.
 *
 * @param <T> the type of data stored in the node.
 */
@Getter
@Setter
public class Node<T> {

    /** The data stored in the node. */
    private T data;

    /** Reference to the left child. */
    private Node<T> left;

    /** Reference to the right child. */
    private Node<T> right;

    /**
     * Constructs a new BST node with the specified data.
     *
     * @param data the data to store.
     */
    public Node(T data) {
        this.data = data;
    }

}