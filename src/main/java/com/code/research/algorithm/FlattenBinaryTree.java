package com.code.research.algorithm;

/**
 * Flatten binary tree to linked list in-place (preorder: root -> left -> right).
 */
public final class FlattenBinaryTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int v) {
            val = v;
        }

        TreeNode(int v, TreeNode l, TreeNode r) {
            val = v;
            left = l;
            right = r;
        }
    }

    // ---- Solution 1: Reverse-preorder recursion (clean + short) ----
    // Traverse: right -> left, keep a "prev" pointer to stitch a singly-right list.
    private static TreeNode prev; // last processed node in the flattened list

    public static void flatten(TreeNode root) {
        prev = null;                 // reset for each call
        dfs(root);
    }

    private static void dfs(TreeNode node) {
        if (node == null) return;          // base
        dfs(node.right);                   // process right subtree first
        dfs(node.left);                    // then left subtree
        node.right = prev;                 // next in list = previously seen node
        node.left = null;                  // left must be null in the list
        prev = node;                       // advance "prev" to current
    }

    // ---- Optional: Iterative O(1) extra space (Morris-style) ----
    // For each node with a left child, find its rightmost node in left subtree,
    // connect that rightmost's right to current.right, move left to right, null left.
    public static void flattenIterative(TreeNode root) {
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left != null) {
                TreeNode pre = cur.left;
                while (pre.right != null) pre = pre.right; // rightmost of left
                pre.right = cur.right;                     // splice original right
                cur.right = cur.left;                      // move left chain to right
                cur.left = null;                           // nullify left
            }
            cur = cur.right;                               // advance along the list
        }
    }

    // tiny demo
    public static void main(String[] args) {
        TreeNode r = new TreeNode(1,
                new TreeNode(2, new TreeNode(3), new TreeNode(4)),
                new TreeNode(5, null, new TreeNode(6))
        );
        flatten(r); // or flattenIterative(r)

        // print flattened list: should be 1 -> 2 -> 3 -> 4 -> 5 -> 6
        for (TreeNode p = r; p != null; p = p.right) {
            System.out.print(p.val + (p.right != null ? " -> " : "\n"));
            if (p.left != null) throw new AssertionError("Left should be null");
        }
    }
}
