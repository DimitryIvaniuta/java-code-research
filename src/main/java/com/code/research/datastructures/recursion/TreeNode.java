package com.code.research.datastructures.recursion;

/**
 * TreeNode represents a node in a binary tree.
 *
 * @param <T> the type of data stored in the node.
 */
public class TreeNode<T> {

    public T data;

    public TreeNode<T> left;

    public TreeNode<T> right;

    /**
     * Constructs a TreeNode with the specified data.
     *
     * @param data the data to store in the node.
     */
    public TreeNode(T data) {
        this.data = data;
    }

}

