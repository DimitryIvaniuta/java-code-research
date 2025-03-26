package com.code.research.datastructures.lists;

import lombok.extern.slf4j.Slf4j;

/**
 * A utility class for sorting unsorted linked list nodes using Merge Sort.
 */
@Slf4j
public class LinkedListSort {

    /**
     * Definition for singly-linked list node.
     */
    public static class ListNode {
        public int value;
        public ListNode next;

        /**
         * Constructs a new ListNode with the specified value.
         *
         * @param value the integer value of the node.
         */
        public ListNode(int value) {
            this.value = value;
            this.next = null;
        }
    }

    /**
     * Sorts an unsorted linked list using the Merge Sort algorithm.
     *
     * @param head the head of the unsorted linked list.
     * @return the head of the sorted linked list.
     */
    public static ListNode sortList(ListNode head) {
        // Base case: if the list is empty or has only one element, it is already sorted.
        if (head == null || head.next == null) {
            return head;
        }

        // Split the list into two halves.
        ListNode middle = getMiddle(head);
        ListNode nextToMiddle = middle.next;
        middle.next = null; // Break the list into two parts.

        // Recursively sort the left and right halves.
        ListNode left = sortList(head);
        ListNode right = sortList(nextToMiddle);

        // Merge the two sorted halves.
        return mergeTwoSortedLists(left, right);
    }

    /**
     * Finds the middle node of the linked list using the slow and fast pointer approach.
     *
     * @param head the head of the linked list.
     * @return the middle node of the list.
     */
    private static ListNode getMiddle(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * Merges two sorted linked lists into one sorted linked list.
     *
     * @param l1 the head of the first sorted linked list.
     * @param l2 the head of the second sorted linked list.
     * @return the head of the merged sorted linked list.
     */
    private static ListNode mergeTwoSortedLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
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
        // Attach any remaining nodes from l1 or l2.
        tail.next = (l1 != null) ? l1 : l2;
        return dummy.next;
    }

    /**
     * Utility method to print the linked list.
     *
     * @param head the head of the linked list.
     */
    public static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.value + " -> ");
            head = head.next;
        }
        System.out.println("null");
    }

    /**
     * Main method demonstrating sorting an unsorted linked list.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Create an unsorted linked list: 4 -> 2 -> 1 -> 3 -> null
        ListNode head = new ListNode(4);
        head.next = new ListNode(2);
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(3);
        head.next.next.next.next = new ListNode(7);
        head.next.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next.next = new ListNode(6);

        log.info("Unsorted list:");
        printList(head);

        // Sort the linked list.
        head = sortList(head);

        log.info("Sorted list:");
        printList(head);
    }

}
