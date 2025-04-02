package com.code.research.datastructures.queues.taskpriority;

import lombok.extern.slf4j.Slf4j;

import java.util.PriorityQueue;
import java.util.Queue;

@Slf4j
public class TaskPriorityQueueApplication {

    public static void main(String[] args) {
        // PriorityQueue to order tasks based on natural ordering (priority).
        Queue<Task> taskQueue = new PriorityQueue<>();

        taskQueue.add(new Task(5, "Low priority task"));
        taskQueue.add(new Task(1, "High priority task"));
        taskQueue.add(new Task(3, "Medium priority task"));

        // Polling tasks from the queue will retrieve the highest-priority task first.
        while (!taskQueue.isEmpty()) {
            Task task = taskQueue.poll();
            log.info("Executing: {}", task);
        }
    }

}
