package com.code.research.datastructures.queues;

import lombok.extern.slf4j.Slf4j;

import java.util.Deque;
import java.util.LinkedList;

@Slf4j
public class DequeBrowserHistoryApplication {

    public static void main(String[] args) {
        // Using Deque to simulate browser history.
        Deque<String> history = new LinkedList<>();

        // Simulate visiting pages.
        history.push("google.com");        // Current page.
        history.push("stackoverflow.com");   // Next page.
        history.push("github.com");          // Next page.

        // Peek at the current page.
        log.info("Current page: {}", history.peek());

        // User presses back: pop current page.
        String lastVisited = history.pop();
        log.info("Going back from {} to {}", lastVisited, history.peek());

        // Visiting a new page clears the forward history (if maintained separately).
        history.push("oracle.com");
        log.info("New current page: " + history.peek());

        // Print full history (from current page backwards).
        log.info("Browsing history:");
        history.forEach(page -> log.info("{}", page));

        log.info("Browsing history peak: {}", history.peek());
        log.info("Browsing history poll: {}", history.poll());
        log.info("Browsing history pop: {}", history.pop());
        history.forEach(page -> log.info("{}", page));
    }
    
}
