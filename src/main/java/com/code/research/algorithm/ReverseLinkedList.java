package com.code.research.algorithm;

public class ReverseLinkedList {

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode reverseList(ListNode head) {
        // new tail will point to null
        ListNode prev = null;
        ListNode cur = head;
        // walk the list
        while (cur != null) {
            // save next node
            ListNode nxt = cur.next;
            // reverse pointer
            cur.next = prev;
            // move prev forward
            prev = cur;
            // move cur forward
            cur = nxt;
        }
        // prev is new head
        return prev;
    }

    public ListNode reverseListRec(ListNode head) {
        return rev(head, null);
    }
    private ListNode rev(ListNode cur, ListNode prev) {
        if (cur == null) return prev;
        ListNode nxt = cur.next;
        cur.next = prev;
        return rev(nxt, cur);
    }

}

