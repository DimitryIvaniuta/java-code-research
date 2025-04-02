package com.code.research.datastructures.queues.taskpriority;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskApplication {

    public static void main(String[] args) {
        Task task1 = new Task(1, "High priority task");
        Task task2 = new Task(1, "High priority task");
        Task task3 = new Task(1, "Another high priority task");
        Task task4 = new Task(2, "Low priority task");

        log.info("task1.compareTo(task2): {}", task1.compareTo(task2)); // Expected 0
        log.info("task1.equals(task2): {}", task1.equals(task2));       // Expected true

        log.info("task1.compareTo(task3): {}", task1.compareTo(task3)); // Non-zero because descriptions differ
        log.info("task1.equals(task3): {}", task1.equals(task3));       // Expected false

        log.info("task1.compareTo(task4): {}", task1.compareTo(task4)); // Negative because 1 < 2
    }

}
