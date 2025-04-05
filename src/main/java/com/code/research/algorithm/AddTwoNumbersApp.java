package com.code.research.algorithm;

public class AddTwoNumbersApp {

    /**
     * Utility method to create a linked list from an array of integers.
     *
     * @param values An array of integer values.
     * @return The head of the newly created linked list.
     */
    public static AddTwoNumbers.ListNode createList(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        AddTwoNumbers.ListNode head = new AddTwoNumbers.ListNode(values[0]);
        AddTwoNumbers.ListNode current = head;
        for (int i = 1; i < values.length; i++) {
            current.next = new AddTwoNumbers.ListNode(values[i]);
            current = current.next;
        }
        return head;
    }

    /**
     * Utility method to print the linked list in a readable format.
     *
     * @param head The head of the linked list to be printed.
     */
    public static void printList(AddTwoNumbers.ListNode head) {
        AddTwoNumbers.ListNode current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println();
    }

    /**
     * The application's entry point that demonstrates the addition of two numbers.
     *
     * Example:
     *   List 1: 2 -> 4 -> 3 (represents the number 342)
     *   List 2: 5 -> 6 -> 4 (represents the number 465)
     *   Sum   : 7 -> 0 -> 8 (represents the number 807)
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Example input: two numbers represented as arrays in reverse order.
        int[] num1 = {2, 4, 3};  // Represents 342
        int[] num2 = {5, 6, 4};  // Represents 465

        // Create linked lists from the input arrays.
        AddTwoNumbers.ListNode l1 = createList(num1);
        AddTwoNumbers.ListNode l2 = createList(num2);

        // Display the input linked lists.
        System.out.print("List 1: ");
        printList(l1);
        System.out.print("List 2: ");
        printList(l2);

        // Compute the sum.
        AddTwoNumbers.ListNode result = AddTwoNumbers.addTwoNumbers(l1, l2);

        // Display the result linked list.
        System.out.print("Sum   : ");
        printList(result);
    }

}

