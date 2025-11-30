package com.code.research.algorithm;

public class TwoNumbersAdd {

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * Adds two numbers represented by linked lists in reverse order.
     *
     * @param l1 head of first list (least‐significant digit first)
     * @param l2 head of second list
     * @return head of a new list representing the sum
     */
    private static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode cur = result;
        int carry = 0;

        // iterate while either list has nodes or there is a carry
        while (l1 != null || l2 != null || carry != 0) {
            int x = l1 != null ? l1.val : 0;
            int y = l2 != null ? l2.val : 0;
            int sum = x + y + carry;
            carry = sum / 10;
            cur.next = new ListNode(sum % 10);
            cur = cur.next;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        return result.next;

    }

    public static void main(String[] args) {
        // create first number 342 => [2,4,3]
        ListNode a = new ListNode(2);
        a.next = new ListNode(4);
        a.next.next = new ListNode(3);

        // create second number 465 => [5,6,4]
        ListNode b = new ListNode(5);
        b.next = new ListNode(6);
        b.next.next = new ListNode(4);

        ListNode result = addTwoNumbers(a, b);

        // print result: should be 807 => [7,0,8]
        while (result != null) {
            System.out.print(result.val);
            if (result.next != null) System.out.print(" -> ");
            result = result.next;
        }
        // Output: 7 -> 0 -> 8
    }
}
