package com.code.research.algorithm.tasks;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class RotateList {
    static final class ListNode {
        int val;
        ListNode next;

        ListNode(int v) {
            this.val = v;
        }

        @Override
        public String toString() {
            return val + (next != null ? "->" + next : "");
        }
    }

    /**
     * Rotates a singly linked list to the right by k positions.
     * Negative k rotates left by |k|.
     */
    public static ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) return head;

        // 1) Find length n and tail
        int n = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            n++;
        }

        // 2) Normalize k into [0, n)
        int r = ((k % n) + n) % n;
        if (r == 0) return head;

        // 3) Make circular, then break after newTail (n - r - 1 steps from head)
        tail.next = head; // circle
        int stepsToNewTail = n - r - 1;
        ListNode newTail = head;
        for (int i = 0; i < stepsToNewTail; i++) newTail = newTail.next;

        ListNode newHead = newTail.next;
        newTail.next = null; // break circle
        return newHead;
    }

    // --- Helpers for a quick demo ---
    static ListNode from(int... a) {
        ListNode dummy = new ListNode(0), curr = dummy;
        for (int v : a) {
            curr.next = new ListNode(v);
            curr = curr.next;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode head = from(1, 2, 3, 4, 5);
        log.info("Rotate right: {}", rotateRight(head, 2));   // 4->5->1->2->3
        log.info("Rotate left: {}", rotateRight(from(1, 2, 3), -1)); // left-rotate by 1 => 2->3->1
    }
}
