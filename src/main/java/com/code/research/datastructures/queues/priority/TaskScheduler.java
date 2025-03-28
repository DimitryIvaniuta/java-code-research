package com.code.research.datastructures.queues.priority;

import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * TaskScheduler demonstrates a scheduling system using a PriorityQueue.
 *
 * <p>Tasks are scheduled based on their priority (and deadline as a tie-breaker).
 * The scheduler processes tasks in the order determined by the priority queue.
 */
@Slf4j
public class TaskScheduler {

    /**
     * The priority queue that orders tasks.
     *
     * <p>Tasks with lower priority numbers are considered more important.
     * If two tasks have the same priority, the one with the earlier deadline comes first.
     */
    private final PriorityQueue<Task> taskQueue;

    /**
     * Constructs a new TaskScheduler.
     */
    public TaskScheduler() {
        // Create a PriorityQueue with a custom comparator for Task objects.
        // Compare by priority first (lower value is higher priority)
        // If priorities are equal, compare by deadline (earlier deadline first)
        taskQueue = new PriorityQueue<>(Comparator
                .comparingInt(Task::getPriority)
                .thenComparingLong(Task::getDeadline));
    }

    /**
     * Adds a task to the scheduler.
     *
     * @param task the task to be added.
     */
    public void addTask(Task task) {
        taskQueue.offer(task);
        log.info("Task added: {}", task);
    }

    /**
     * Processes tasks one by one in the order of their priority.
     *
     * <p>This simulates the execution of tasks.
     */
    public void processTasks() {
        log.info("Processing tasks:");
        while (!taskQueue.isEmpty()) {
            Task task = taskQueue.poll();
            // Simulate processing of the task (here we just print it)
            log.info("Processing: {}", task);
        }
    }

}