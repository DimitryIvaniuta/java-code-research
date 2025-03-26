package com.code.research.datastructures.lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LinkedListAlgorithms {

    // Definition for singly-linked list.
    public static class ListNode {
        int value;
        ListNode next;

        ListNode(int value) {
            this.value = value;
            this.next = null;
        }
    }

    /**
     * Reverses a singly linked list.
     *
     * @param head The head of the linked list.
     * @return The new head of the reversed linked list.
     */
    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next; // store next node
            curr.next = prev;              // reverse the link
            prev = curr;                   // move prev forward
            curr = nextTemp;               // move current forward
        }
        return prev;
    }


    /**
     * Merges two sorted linked lists into one sorted linked list.
     *
     * @param l1 The head of the first sorted linked list.
     * @param l2 The head of the second sorted linked list.
     * @return The head of the merged sorted linked list.
     */
    public static ListNode mergeTwoSortedLists(ListNode l1, ListNode l2) {
        // Dummy node acts as the starting point of the merged list.
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        // Traverse both lists, appending the smaller value each time.
        while (l1 != null && l2 != null) {
            if (l1.value <= l2.value) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next;
        }

        // Append any remaining nodes from either list.
        tail.next = (l1 != null) ? l1 : l2;
        return dummy.next;
    }

    /**
     * Detects if a linked list contains a cycle using Floyd's Tortoise and Hare algorithm.
     *
     * @param head The head of the linked list.
     * @return True if a cycle is detected, otherwise false.
     */
    public static boolean hasCycle(ListNode head) {
        if (head == null) return false;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;            // move slow pointer by one
            fast = fast.next.next;       // move fast pointer by two
            if (slow == fast) {          // a meeting point indicates a cycle
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to print the linked list.
     *
     * @param head The head of the linked list.
     */
    public static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            log.info( "{} -> ", current.value);
            current = current.next;
        }
        log.info("null");
    }

    public static void main(String[] args) {
        // Example 1: Merge two sorted linked lists.
        // First sorted list: 1 -> 3 -> 5
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(3);
        l1.next.next = new ListNode(5);

        // Second sorted list: 2 -> 4 -> 6
        ListNode l2 = new ListNode(2);
        l2.next = new ListNode(4);
        l2.next.next = new ListNode(6);

        log.info("First sorted list:");
        printList(l1);
        log.info("Second sorted list:");
        printList(l2);

        // Merge the two sorted lists.
        ListNode merged = mergeTwoSortedLists(l1, l2);
        log.info("Merged sorted list:");
        printList(merged);

        // Example 2: Reverse the merged linked list.
        ListNode reversed = reverseList(merged);
        log.info("Reversed merged list:");
        printList(reversed);

        // Example 3: Detect cycle in a linked list.
        // Create a list: 10 -> 20 -> 30 -> 40, then introduce a cycle: 40 -> 20
        ListNode cycleTest = new ListNode(10);
        cycleTest.next = new ListNode(20);
        cycleTest.next.next = new ListNode(30);
        cycleTest.next.next.next = new ListNode(40);
        // Introduce a cycle here.
        cycleTest.next.next.next.next = cycleTest.next;

        boolean cycleExists = hasCycle(cycleTest);
        log.info("Cycle detected in cycleTest list: {}", cycleExists);
    }
}
