package com.code.research.datastructures.trees.treeprocessor;

import com.code.research.datastructures.graph.Pair;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;
import java.util.function.LongFunction;

/**
 * BinaryTreeProcessor provides a suite of methods for constructing,
 * traversing, and manipulating a binary tree.
 *
 * <p>This class includes methods for:
 * <ul>
 *   <li>Constructing a sample tree.</li>
 *   <li>Recursive tree traversals: pre-order, in-order, post-order.</li>
 *   <li>Iterative in-order and level-order traversals.</li>
 *   <li>Computing the height of a tree.</li>
 *   <li>Checking if a tree is balanced.</li>
 *   <li>Creating a mirror image of a tree.</li>
 *   <li>Searching for a node by key.</li>
 *   <li>Finding the lowest common ancestor (LCA) of two nodes.</li>
 * </ul>
 *
 * <p>Note: The {@code constructTree} method builds a sample tree using hard-coded Integer values,
 * so it assumes T is Integer. For a truly generic tree construction, you could modify the method
 * to accept a generator function.
 *
 * @param <T> the type of data stored in the tree nodes.
 */
@Slf4j
public class BinaryTreeProcessor<T> {


    /**
     * Constructs a sample binary tree:
     *
     * <pre>
     *         1
     *       /   \
     *      2     3
     *     / \
     *    4   5
     * </pre>
     * <p>
     * Note: This method assumes T is Integer.
     *
     * @return the root of the constructed tree.
     */
    public TreeNode<T> constructTree(LongFunction<T> converter) {

        TreeNode<T> root = new TreeNode<>(converter.apply(1));
        root.left = new TreeNode<>(converter.apply(2));
        root.right = new TreeNode<>(converter.apply(3));
        root.left.left = new TreeNode<>(converter.apply(4));
        root.left.right = new TreeNode<>(converter.apply(5));
        return (TreeNode<T>) root;
    }


    /**
     * Performs a recursive pre-order traversal of the tree.
     * (Root, Left, Right)
     *
     * @param root the root of the tree.
     */
    public void preOrder(TreeNode<T> root) {
        if (root == null) return;
        log.info(" {}", root.data);
        preOrder(root.left);
        preOrder(root.right);
    }

    /**
     * Performs a recursive in-order traversal of the tree.
     * (Left, Root, Right)
     *
     * @param root the root of the tree.
     */
    public void inOrder(TreeNode<T> root) {
        if (root == null) return;
        inOrder(root.left);
        log.info(" {}", root.data);
        inOrder(root.right);
    }

    /**
     * Performs a recursive post-order traversal of the tree.
     * (Left, Right, Root)
     *
     * @param root the root of the tree.
     */
    public void postOrder(TreeNode<T> root) {
        if (root == null) return;
        postOrder(root.left);
        postOrder(root.right);
        log.info(" {}", root.data);
    }

    /**
     * Performs a level-order (breadth-first) traversal of the tree.
     *
     * @param root the root of the tree.
     */
    public void levelOrder(TreeNode<T> root) {
        if (root == null) return;
        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode<T> current = queue.poll();
            log.info(current.data + " ");
            if (current.left != null) queue.offer(current.left);
            if (current.right != null) queue.offer(current.right);
        }
    }

    /**
     * Performs an iterative in-order traversal of the tree.
     *
     * @param root the root of the tree.
     */
    public void inOrderIterative(TreeNode<T> root) {
        Stack<TreeNode<T>> stack = new Stack<>();
        TreeNode<T> current = root;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            log.info(current.data + " ");
            current = current.right;
        }
    }

    /**
     * Computes the height (maximum depth) of the tree.
     *
     * @param root the root of the tree.
     * @return the height of the tree.
     */
    public int height(TreeNode<T> root) {
        if (root == null) return 0;
        return 1 + Math.max(height(root.left), height(root.right));
    }

    /**
     * Checks if the binary tree is balanced.
     * A tree is balanced if, at every node, the difference in height between the left and right subtrees is no more than 1.
     *
     * @param root the root of the tree.
     * @return true if the tree is balanced; false otherwise.
     */
    public boolean isBalanced(TreeNode<T> root) {
        return checkBalance(root) != -1;
    }

    /**
     * Helper method to check the balance of the tree.
     * Returns the height of the tree if balanced, or -1 if unbalanced.
     *
     * @param root the root of the tree.
     * @return the height or -1 if unbalanced.
     */
    private int checkBalance(TreeNode<T> root) {
        if (root == null) return 0;
        int leftHeight = checkBalance(root.left);
        if (leftHeight == -1) return -1;
        int rightHeight = checkBalance(root.right);
        if (rightHeight == -1) return -1;
        if (Math.abs(leftHeight - rightHeight) > 1) return -1;
        return 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * Creates a mirror image of the binary tree by swapping left and right children recursively.
     *
     * @param root the root of the tree.
     * @return the root of the mirrored tree.
     */
    public TreeNode<T> mirror(TreeNode<T> root) {
        if (root == null) return null;
        TreeNode<T> left = mirror(root.left);
        TreeNode<T> right = mirror(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    /**
     * Searches for a node with the specified key in the binary tree.
     *
     * @param root the root of the tree.
     * @param key  the key to search for.
     * @return the node containing the key, or null if not found.
     */
    public TreeNode<T> search(TreeNode<T> root, T key) {
        if (root == null) return null;
        if (root.data.equals(key)) return root;
        TreeNode<T> leftResult = search(root.left, key);
        if (leftResult != null) return leftResult;
        return search(root.right, key);
    }

    /**
     * Finds the lowest common ancestor (LCA) of two nodes in the binary tree.
     * Assumes both nodes exist in the tree.
     *
     * @param root the root of the tree.
     * @param p    one node.
     * @param q    the other node.
     * @return the lowest common ancestor of p and q.
     */
    public TreeNode<T> lowestCommonAncestor(TreeNode<T> root, TreeNode<T> p, TreeNode<T> q) {
        if (root == null || root == p || root == q) return root;
        TreeNode<T> left = lowestCommonAncestor(root.left, p, q);
        TreeNode<T> right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) return root;
        return left != null ? left : right;
    }

    /**
     * Performs a level-order traversal of the binary tree and returns the nodes grouped by level.
     *
     * @param root the root of the tree.
     * @return a list of lists, where each inner list contains the node data for a level of the tree.
     */
    public List<List<T>> levelOrderList(TreeNode<T> root) {
        List<List<T>> levels = new ArrayList<>();
        if (root == null) return levels;
        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<T> currentLevel = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                TreeNode<T> node = queue.poll();
                if (node == null) {
                    continue;
                }
                currentLevel.add(node.data);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            levels.add(currentLevel);
        }
        return levels;
    }

    public List<List<Integer>> verticalOrderTraversal(TreeNode<Integer> root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Map<Integer, List<Integer>> map = new TreeMap<>();
        Queue<Pair<TreeNode<Integer>, Integer>> queue = new LinkedList<>();
        queue.offer(new Pair<>(root, 0));

        while (!queue.isEmpty()) {
            Pair<TreeNode<Integer>, Integer> pair = queue.poll();
            TreeNode<Integer> node = pair.getKey();
            int hd = pair.getValue();

            map.computeIfAbsent(hd, k -> new ArrayList<>()).add(node.data);

            if (node.left != null) queue.offer(new Pair<>(node.left, hd - 1));
            if (node.right != null) queue.offer(new Pair<>(node.right, hd + 1));
        }

        result.addAll(map.values());
        return result;
    }

}
