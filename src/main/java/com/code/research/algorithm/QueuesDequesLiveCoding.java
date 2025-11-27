package com.code.research.algorithm;

import java.util.*;

/**
 * Must-know live-coding patterns with Queue / Deque (Java).
 * Short, obvious, interview-ready with comments.
 */
public final class QueuesDequesLiveCoding {

    /* ----------------------------------------------------------------------
     * 1) BFS Level Order Traversal (Binary Tree) — classic Queue usage
     * ---------------------------------------------------------------------- */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> out = new ArrayList<>();
        if (root == null) return out;                     // edge case
        Queue<TreeNode> q = new ArrayDeque<>();           // FIFO
        q.offer(root);                                    // start with root
        while (!q.isEmpty()) {
            int size = q.size();                          // nodes on this level
            List<Integer> lvl = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TreeNode n = q.poll();                    // pop front
                lvl.add(n.val);
                if (n.left != null) q.offer(n.left);    // push children
                if (n.right != null) q.offer(n.right);
            }
            out.add(lvl);                                 // append level
        }
        return out;
    }

    /* ----------------------------------------------------------------------
     * 2) Sliding Window Maximum — Deque keeps indices in decreasing values
     *    Time O(n), Space O(k)
     * ---------------------------------------------------------------------- */
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (k <= 0) return new int[0];
        int n = nums.length;
        int[] ans = new int[Math.max(0, n - k + 1)];
        Deque<Integer> dq = new ArrayDeque<>();           // store indices, front = max
        for (int i = 0; i < n; i++) {
            while (!dq.isEmpty() && dq.peekFirst() <= i - k) dq.pollFirst();   // drop out-of-window
            while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) dq.pollLast(); // drop smaller tail
            dq.offerLast(i);                              // push current index
            if (i >= k - 1) ans[i - k + 1] = nums[dq.peekFirst()]; // window ready -> record max
        }
        return ans;
    }

    /* ----------------------------------------------------------------------
     * 3) First Negative Number in every Window of size k — Deque of indices of negatives
     *    Time O(n), Space O(k)
     * ---------------------------------------------------------------------- */
    public static int[] firstNegativeInWindow(int[] a, int k) {
        int n = a.length;
        if (k <= 0 || n == 0) return new int[0];
        int[] ans = new int[Math.max(0, n - k + 1)];
        Deque<Integer> dq = new ArrayDeque<>();           // indices of negatives
        for (int i = 0; i < n; i++) {
            if (a[i] < 0) dq.offerLast(i);                // track negatives
            while (!dq.isEmpty() && dq.peekFirst() <= i - k) dq.pollFirst(); // slide
            if (i >= k - 1) ans[i - k + 1] = dq.isEmpty() ? 0 : a[dq.peekFirst()];
        }
        return ans;
    }

    /* ----------------------------------------------------------------------
     * 4) RecentCounter (LeetCode 933) — keep only last 3000ms pings in a Queue
     * ---------------------------------------------------------------------- */
    public static final class RecentCounter {
        private final Deque<Integer> q = new ArrayDeque<>(); // timestamps in ms (increasing)

        public int ping(int t) {
            q.offerLast(t);                               // add new ping
            while (q.peekFirst() < t - 3000) q.pollFirst(); // drop old ones
            return q.size();                              // pings in [t-3000, t]
        }
    }

    /* ----------------------------------------------------------------------
     * 5) Moving Average from Data Stream — Queue + running sum
     * ---------------------------------------------------------------------- */
    public static final class MovingAverage {
        private final int size;                           // window size
        private final Deque<Integer> q = new ArrayDeque<>();
        private long sum = 0;

        public MovingAverage(int size) {
            this.size = size;
        }

        public double next(int val) {
            q.offerLast(val);
            sum += val;                 // push new
            if (q.size() > size) sum -= q.pollFirst();    // pop oldest if over size
            return sum * 1.0 / q.size();                  // average of current window
        }
    }

    /* ----------------------------------------------------------------------
     * 6) Implement Stack using a Deque — trivial and idiomatic in Java
     * ---------------------------------------------------------------------- */
    public static final class MyStack {
        private final Deque<Integer> d = new ArrayDeque<>();

        public void push(int x) {
            d.push(x);
        }            // push to front

        public int pop() {
            return d.pop();
        }          // pop from front

        public int top() {
            return d.peek();
        }         // peek front

        public boolean empty() {
            return d.isEmpty();
        }     // empty?
    }

    /* ----------------------------------------------------------------------
     * 7) Implement Queue using two Stacks — classic interview pattern
     * ---------------------------------------------------------------------- */
    public static final class MyQueue {
        private final Deque<Integer> in = new ArrayDeque<>();  // incoming
        private final Deque<Integer> out = new ArrayDeque<>(); // outgoing

        public void push(int x) {
            in.push(x);
        }            // O(1)

        public int pop() {
            move();
            return out.pop();
        }   // amortized O(1)

        public int peek() {
            move();
            return out.peek();
        }  // amortized O(1)

        public boolean empty() {
            return in.isEmpty() && out.isEmpty();
        }

        private void move() {
            if (out.isEmpty()) while (!in.isEmpty()) out.push(in.pop());
        } // flip once
    }

    /* ----------------------------------------------------------------------
     * 8) Monotonic Queue (max) — reusable as a tiny helper with Deque
     * ---------------------------------------------------------------------- */
    public static final class MonoQueueMax {
        private final Deque<Integer> dq = new ArrayDeque<>(); // store values (non-increasing)

        public void push(int x) {
            while (!dq.isEmpty() && dq.peekLast() < x) dq.pollLast();
            dq.offerLast(x);
        }

        public void pop(int x) {
            if (!dq.isEmpty() && dq.peekFirst() == x) dq.pollFirst();
        }

        public int max() {
            return dq.peekFirst();
        }
    }

    /* -------------------------- Support tree node -------------------------- */
    public static final class TreeNode {
        public int val;
        public TreeNode left, right;

        public TreeNode(int v) {
            val = v;
        }

        public TreeNode(int v, TreeNode l, TreeNode r) {
            val = v;
            left = l;
            right = r;
        }
    }

    /* -------------------------------- demo -------------------------------- */
    public static void main(String[] args) {
        // 1) BFS
        TreeNode r = new TreeNode(1, new TreeNode(2, new TreeNode(4), null),
                new TreeNode(3, null, new TreeNode(5)));
        System.out.println(levelOrder(r)); // [[1],[2,3],[4,5]]

        // 2) Sliding window max
        System.out.println(Arrays.toString(maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3))); // [3,3,5,5,6,7]

        // 3) First negative in window
        System.out.println(Arrays.toString(firstNegativeInWindow(new int[]{12, -1, -7, 8, -15, 30, 16, 28}, 3))); // [-1,-1,-7,-15,-15,0]

        // 4) RecentCounter
        RecentCounter rc = new RecentCounter();
        System.out.println(rc.ping(1));
        System.out.println(rc.ping(100));
        System.out.println(rc.ping(3001));
        System.out.println(rc.ping(3002)); // 1,2,3,3

        // 5) MovingAverage
        MovingAverage ma = new MovingAverage(3);
        System.out.println(ma.next(1));
        System.out.println(ma.next(10));
        System.out.println(ma.next(3));
        System.out.println(ma.next(5)); // 1.0, 5.5, 4.666..., 6.0

        // 6) Stack via Deque
        MyStack st = new MyStack();
        st.push(10);
        st.push(20);
        System.out.println(st.top()); // 20

        // 7) Queue via two stacks
        MyQueue mq = new MyQueue();
        mq.push(1);
        mq.push(2);
        System.out.println(mq.peek()); // 1

        // 8) Monotonic Queue
        MonoQueueMax mqm = new MonoQueueMax();
        mqm.push(1);
        mqm.push(3);
        mqm.push(2);
        System.out.println(mqm.max()); // 3
        mqm.pop(3);
        System.out.println(mqm.max()); // 2
    }
}
