package com.code.research.collections;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

@Slf4j
public class LinkedListExample {

    public static void main(String[] args) {
        List<String> list = new LinkedList<>();
        list.add("Alice");
        list.add("Bob");
        list.add(0, "Charlie");  // Insertion at the head
        log.info("LinkedList: {}", list);

        // As a Deque, LinkedList also supports:
        LinkedList<String> deque = (LinkedList<String>) list;
        deque.addFirst("Dave");
        deque.addLast("Eve");
        log.info("Deque view: {}", deque);
    }

}
