package com.code.research.datastructures.queues;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Demonstrates real-world queue algorithms using a print queue simulation.
 */
@Slf4j
public class QueueAlgorithmsDemo {

    public static void main(String[] args) {
        PrinterQueue printerQueue = new PrinterQueue();

        // Add sample print jobs to the queue.
        printerQueue.addJob(new PrintJob(1, "Invoice.pdf", 12));
        printerQueue.addJob(new PrintJob(2, "Report.docx", 8));
        printerQueue.addJob(new PrintJob(3, "Presentation.ppt", 20));

        // Process all print jobs in FIFO order.
        printerQueue.processAllJobs();

        Queue<String> jobQueue = new LinkedList<>();
        jobQueue.add("q1");
        jobQueue.add("q2");
        jobQueue.add("q3");
        jobQueue.add("q4");
        log.info("Q1:{} Q2:{}", jobQueue.poll(), jobQueue.poll());
        log.info("Q3:{} Q4:{}", jobQueue.poll(), jobQueue.poll());
        log.info("Q5:{} Q Empty:{}", jobQueue.poll(), jobQueue.isEmpty());
    }

}
