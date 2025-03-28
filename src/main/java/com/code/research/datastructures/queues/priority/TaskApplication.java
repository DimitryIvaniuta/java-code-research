package com.code.research.datastructures.queues.priority;

public class TaskApplication {

    /**
     * Main method demonstrating the usage of TaskScheduler with a PriorityQueue.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler();

        // Current timestamp for demonstration purposes.
        long now = System.currentTimeMillis();

        // Add tasks with different priorities and deadlines.
        scheduler.addTask(new Task("Task A", 2, now + 5000));
        scheduler.addTask(new Task("Task B", 1, now + 10000));
        scheduler.addTask(new Task("Task C", 1, now + 3000));
        scheduler.addTask(new Task("Task D", 3, now + 2000));
        scheduler.addTask(new Task("Task E", 2, now + 4000));

        // Process tasks in order.
        scheduler.processTasks();
    }


}
