package com.code.research.datastructures.queues;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
public class TicketQueueDemo {

    // A simple Ticket class with an id and description.
    static class Ticket {
        private final int id;
        private final String description;

        public Ticket(int id, String description) {
            this.id = id;
            this.description = description;
        }

        @Override
        public String toString() {
            return "Ticket{id=" + id + ", description='" + description + "'}";
        }
    }

    public static void main(String[] args) {
        // Using LinkedList as a FIFO queue via the Queue interface.
        Queue<Ticket> ticketQueue = new LinkedList<>();

        // Adding tickets to the queue.
        ticketQueue.add(new Ticket(1, "Issue with login"));
        ticketQueue.add(new Ticket(2, "Bug in payment processing"));
        ticketQueue.add(new Ticket(3, "Feature request: Dark Mode"));

        // Processing tickets in FIFO order.
        while (!ticketQueue.isEmpty()) {
            Ticket ticket = ticketQueue.poll(); // retrieves and removes the head
            log.info("Processing: {}", ticket);
        }
    }
}
