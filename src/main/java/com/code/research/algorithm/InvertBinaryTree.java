package com.code.research.algorithm;

public final class InvertBinaryTree {
    public static class TreeNode {
        public int val;
        public TreeNode left, right;
        public TreeNode(int v) { val = v; }
        public TreeNode(int v, TreeNode l, TreeNode r) { val = v; left = l; right = r; }
    }

    // Recursive (cleanest)
    public static TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        TreeNode tmp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(tmp);
        return root;
    }

    // Iterative BFS (no recursion)
    public static TreeNode invertTreeIter(TreeNode root) {
        if (root == null) return null;
        java.util.ArrayDeque<TreeNode> q = new java.util.ArrayDeque<>();
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode n = q.poll();
            TreeNode tmp = n.left; n.left = n.right; n.right = tmp;
            if (n.left != null) q.add(n.left);
            if (n.right != null) q.add(n.right);
        }
        return root;
    }

    public static void main(String[] args) {
        TreeNode r = new TreeNode(4,
                new TreeNode(2, new TreeNode(1), new TreeNode(3)),
                new TreeNode(7, new TreeNode(6), new TreeNode(9))
        );
        invertTree(r); // or invertTreeIter(r)
        // tree is now [4,7,2,9,6,3,1]
    }
}
