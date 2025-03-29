package com.code.research.datastructures.queues.priority;

import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.PriorityQueue;

@Slf4j
public class TaskSchedulerApplication {

    public static void main(String[] args) {
        PriorityQueue<Task> queue = new PriorityQueue<>(Comparator.comparingInt(Task::getPriority));
        queue.offer(new Task("Task1", 3, 4));
        queue.offer(new Task("Task2", 1, 5));
        queue.offer(new Task("Task3", 2, 6));

        while (!queue.isEmpty()) {
            log.info("Processing :: {}", queue.poll());
        }
    }
}
