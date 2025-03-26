package com.code.research.datastructures.lists;

import lombok.extern.slf4j.Slf4j;

/**
 * A collection of real-world algorithms implemented on singly linked lists.
 */
@Slf4j
public class LinkedListMoreAlgorithms {

    /**
     * Definition for singly-linked list node.
     */
    public static class ListNode {
        public int value;
        public ListNode next;

        /**
         * Constructs a new ListNode with the given value.
         *
         * @param value the integer value of the node
         */
        public ListNode(int value) {
            this.value = value;
            this.next = null;
        }
    }

    /**
     * Reverses the given singly linked list.
     *
     * @param head the head of the linked list
     * @return the new head of the reversed linked list
     */
    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    /**
     * Merges two sorted linked lists into one sorted linked list.
     *
     * @param l1 the head of the first sorted linked list
     * @param l2 the head of the second sorted linked list
     * @return the head of the merged sorted linked list
     */
    public static ListNode mergeTwoSortedLists(ListNode l1, ListNode l2) {
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
        tail.next = (l1 != null) ? l1 : l2;
        return dummy.next;
    }

    /**
     * Detects whether a linked list has a cycle using Floyd's Tortoise and Hare algorithm.
     *
     * @param head the head of the linked list
     * @return true if a cycle exists; false otherwise
     */
    public static boolean hasCycle(ListNode head) {
        if (head == null) return false;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes duplicate elements from a sorted linked list.
     *
     * @param head the head of the sorted linked list
     * @return the head of the linked list with duplicates removed
     */
    public static ListNode removeDuplicates(ListNode head) {
        ListNode current = head;
        while (current != null && current.next != null) {
            if (current.value == current.next.value) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
        return head;
    }

    /**
     * Finds and returns the middle node of a linked list.
     * If the list has an even number of nodes, it returns the first of the two middle nodes.
     *
     * @param head the head of the linked list
     * @return the middle node
     */
    public static ListNode findMiddle(ListNode head) {
        if (head == null) return null;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * Partitions a linked list around a pivot value such that all nodes less than the pivot
     * come before nodes greater than or equal to the pivot.
     *
     * @param head  the head of the linked list
     * @param pivot the pivot value to partition around
     * @return the head of the partitioned linked list
     */
    public static ListNode partitionList(ListNode head, int pivot) {
        ListNode beforeStart = null, beforeEnd = null;
        ListNode afterStart = null, afterEnd = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = null;
            if (head.value < pivot) {
                if (beforeStart == null) {
                    beforeStart = head;
                    beforeEnd = beforeStart;
                } else {
                    beforeEnd.next = head;
                    beforeEnd = head;
                }
            } else {
                if (afterStart == null) {
                    afterStart = head;
                    afterEnd = afterStart;
                } else {
                    afterEnd.next = head;
                    afterEnd = head;
                }
            }
            head = next;
        }
        if (beforeStart == null) return afterStart;
        beforeEnd.next = afterStart;
        return beforeStart;
    }

    /**
     * Checks whether a linked list is a palindrome.
     *
     * @param head the head of the linked list
     * @return true if the linked list is a palindrome; false otherwise
     */
    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;
        // Find the middle
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // Reverse second half
        ListNode secondHalfStart = reverseList(slow.next);
        // Check palindrome
        ListNode p1 = head;
        ListNode p2 = secondHalfStart;
        boolean palindrome = true;
        while (p2 != null) {
            if (p1.value != p2.value) {
                palindrome = false;
                break;
            }
            p1 = p1.next;
            p2 = p2.next;
        }
        // Restore the list (optional)
        slow.next = reverseList(secondHalfStart);
        return palindrome;
    }

    /**
     * Utility method to print the linked list.
     *
     * @param head the head of the linked list
     */
    public static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            log.info("{} -> ", current.value);
            current = current.next;
        }
        log.info("null");
    }

    /**
     * Main method demonstrating various linked list operations.
     */
    public static void main(String[] args) {
        // Create sample sorted lists for merging demonstration.
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(3);
        l1.next.next = new ListNode(5);

        ListNode l2 = new ListNode(2);
        l2.next = new ListNode(4);
        l2.next.next = new ListNode(6);

        log.info("Merging two sorted lists:");
        printList(l1);
        printList(l2);
        ListNode merged = mergeTwoSortedLists(l1, l2);
        log.info("Merged list:");
        printList(merged);

        // Reverse the merged list.
        ListNode reversed = reverseList(merged);
        log.info("Reversed merged list:");
        printList(reversed);

        // Remove duplicates from a sorted list.
        // Create list: 1 -> 1 -> 2 -> 3 -> 3 -> 4
        ListNode sortedDupList = new ListNode(1);
        sortedDupList.next = new ListNode(1);
        sortedDupList.next.next = new ListNode(2);
        sortedDupList.next.next.next = new ListNode(3);
        sortedDupList.next.next.next.next = new ListNode(3);
        sortedDupList.next.next.next.next.next = new ListNode(4);
        log.info("List with duplicates:");
        printList(sortedDupList);
        ListNode noDupList = removeDuplicates(sortedDupList);
        log.info("After removing duplicates:");
        printList(noDupList);

        // Find the middle element of a list.
        log.info("Middle of the list:");
        ListNode middle = findMiddle(noDupList);
        log.info("{}",(middle != null) ? middle.value : "null");

        // Partition the list around a pivot value.
        log.info("Partitioning list around pivot value 3:");
        ListNode partitioned = partitionList(noDupList, 3);
        printList(partitioned);

        // Check if a list is a palindrome.
        // Create a palindrome list: 1 -> 2 -> 3 -> 2 -> 1
        ListNode palindromeList = new ListNode(1);
        palindromeList.next = new ListNode(2);
        palindromeList.next.next = new ListNode(3);
        palindromeList.next.next.next = new ListNode(2);
        palindromeList.next.next.next.next = new ListNode(1);
        log.info("Palindrome check on list:");
        printList(palindromeList);
        boolean isPalin = isPalindrome(palindromeList);
        log.info("Is palindrome: " + isPalin);

        // Create a cycle for testing cycle detection.
        ListNode cycleList = new ListNode(10);
        cycleList.next = new ListNode(20);
        cycleList.next.next = new ListNode(30);
        cycleList.next.next.next = new ListNode(40);
        // Create cycle: 40 -> 20
        cycleList.next.next.next.next = cycleList.next;
        boolean cycleDetected = hasCycle(cycleList);
        log.info("Cycle detected: " + cycleDetected);
    }

}
