package com.code.research.algorithm;

public class AddTwoNumbers {

    /**
     * Adds two numbers represented by linked lists in reverse order
     * and returns the sum as a linked list in reverse order.
     *
     * @param l1 the head of the first non-empty linked list
     * @param l2 the head of the second non-empty linked list
     * @return the head of the resulting linked list representing the sum
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // Dummy head to make list manipulation easier
        ListNode dummyHead = new ListNode(0);
        ListNode current = dummyHead;

        // 'carry' will store carryover for sums >= 10
        int carry = 0;

        // Traverse both lists until both are exhausted and no carry remains
        while (l1 != null || l2 != null || carry != 0) {
            // Retrieve current values from l1 and l2; if null, use 0
            int x = (l1 != null) ? l1.val : 0;
            int y = (l2 != null) ? l2.val : 0;

            // Calculate sum and carry
            int sum = x + y + carry;
            carry = sum / 10;

            // Create new node for the digit
            current.next = new ListNode(sum % 10);
            current = current.next;

            // Move forward in lists if possible
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        // Return the actual head of the resulting list
        return dummyHead.next;
    }

    // Definition for singly-linked list.
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
    }

}
