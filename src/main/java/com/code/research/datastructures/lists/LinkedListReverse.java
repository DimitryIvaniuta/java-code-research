package com.code.research.datastructures.lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LinkedListReverse {

    public static class Node {

        int data;

        Node next;

        public Node(int data) {
            this.data = data;
        }

    }

    public static void main(String[] args) {
        Node node1 = new Node(2);
        node1.next = new Node(3);
        node1.next.next = new Node(4);
        node1.next.next.next = new Node(5);
        node1.next.next.next.next = new Node(6);
        node1.next.next.next.next.next = new Node(7);

        log.info("Print linked list");
        printNode(node1);

        Node prev = null;
        Node curr = node1;
        Node reversedHead = null;
        while (curr != null) {
            Node nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            if (nextTemp == null) {
                reversedHead = curr;
            }
            curr = nextTemp;
        }
        log.info("\n\nPrint reversed list:");

        printNode(reversedHead);
    }

    private static void printNode(Node node1) {
        Node nodePrint = node1;
        while (nodePrint != null) {
            log.info("{}, ", nodePrint.data);
            nodePrint = nodePrint.next;

        }
    }
}
