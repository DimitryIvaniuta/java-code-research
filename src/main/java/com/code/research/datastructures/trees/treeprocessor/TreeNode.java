package com.code.research.datastructures.trees.treeprocessor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * TreeNode is a static inner class representing a node in a binary tree.
 *
 * @param <T> the type of data stored in the node.
 */
@Getter
@AllArgsConstructor
@ToString
public class TreeNode<T> {

    T data;

    TreeNode<T> left;

    TreeNode<T> right;

    /**
     * Constructs a TreeNode with the given data.
     *
     * @param data the data to store in the node.
     */
    public TreeNode(T data) {
        this.data = data;
    }
}
