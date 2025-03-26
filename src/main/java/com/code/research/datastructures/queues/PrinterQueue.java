package com.code.research.datastructures.queues;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Simulates a printer queue system using a FIFO queue.
 */
@Slf4j
public class PrinterQueue {
    private final Queue<PrintJob> jobQueue;

    /**
     * Constructs a new PrinterQueue.
     */
    public PrinterQueue() {
        this.jobQueue = new LinkedList<>();
    }

    /**
     * Adds a new print job to the queue.
     *
     * @param job the PrintJob to be added
     */
    public void addJob(PrintJob job) {
        jobQueue.offer(job);
        log.info("Job added: {}", job);
    }

    /**
     * Processes the next print job in the queue.
     * The job is removed from the queue and printed.
     */
    public void processNextJob() {
        if (jobQueue.isEmpty()) {
            log.info("No print jobs in the queue.");
            return;
        }
        PrintJob job = jobQueue.poll();
        log.info("Processing job: {}", job);
        // todo printing logic
        log.info("Completed job: {}", job);
    }

    /**
     * Processes all print jobs in the queue.
     */
    public void processAllJobs() {
        while (!jobQueue.isEmpty()) {
            processNextJob();
        }
    }

}

