package com.code.research.datastructures.queues.priority;

import lombok.Getter;

/**
 * Represents a task with a name, a priority, and a deadline.
 *
 * <p>The lower the priority number, the higher the importance. If two tasks have the same priority,
 * the task with the earlier deadline is processed first.
 */
@Getter
public class Task {

    private final String name;

    private final int priority;

    private final long deadline; // Deadline represented as a timestamp (e.g., milliseconds)

    /**
     * Constructs a new Task with the given name, priority, and deadline.
     *
     * @param name     the name of the task
     * @param priority the priority level of the task (lower value indicates higher priority)
     * @param deadline the deadline timestamp of the task
     */
    public Task(String name, int priority, long deadline) {
        this.name = name;
        this.priority = priority;
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return String.format("Task{name='%s', priority=%d, deadline=%d}", name, priority, deadline);
    }

}

