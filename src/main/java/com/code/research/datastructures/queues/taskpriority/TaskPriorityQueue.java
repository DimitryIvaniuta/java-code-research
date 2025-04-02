package com.code.research.datastructures.queues.taskpriority;

import lombok.Getter;

import java.util.Objects;

public class TaskPriorityQueue {


    // A Task with a priority (lower value = higher priority) and description.
    @Getter
    static class Task implements Comparable<Task> {
        private final int priority;
        private final String description;

        public Task(int priority, String description) {
            this.priority = priority;
            this.description = description;
        }

        @Override
        public int compareTo(Task other) {
            // First compare by priority (lower value = higher priority)
            int cmp = Integer.compare(this.priority, other.priority);
            if (cmp == 0) {
                // If priorities are equal, compare descriptions to enforce total ordering
                cmp = this.description.compareTo(other.description);
            }
            return cmp;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Task task = (Task) o;
            return priority == task.priority && Objects.equals(description, task.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(priority, description);
        }

        @Override
        public String toString() {
            return "Task{priority=" + priority + ", description='" + description + "'}";
        }
    }
}
