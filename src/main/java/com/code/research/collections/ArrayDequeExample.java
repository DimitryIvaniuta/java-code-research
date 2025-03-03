package com.code.research.collections;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;

@Slf4j
public class ArrayDequeExample {

    public static void main(String[] args) {
        Deque<String> deque = new ArrayDeque<>();
        deque.addFirst("Alice");
        deque.addLast("Bob");
        deque.addFirst("Charlie");
        log.info("ArrayDeque: {}", deque);

        // Using ArrayDeque as a stack:
        deque.push("Dave");
        log.info("After push: {}", deque);
        String top = deque.pop();
        log.info("Popped: {}, After pop: {}", top, deque);
        String topNext = deque.pop();
        log.info("Popped: {}, After topNext: {}", topNext, deque);
    }

}
