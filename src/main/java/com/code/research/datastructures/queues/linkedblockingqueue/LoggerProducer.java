package com.code.research.datastructures.queues.linkedblockingqueue;

import lombok.AllArgsConstructor;

import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

@AllArgsConstructor
public class LoggerProducer implements Runnable {

    private final BlockingQueue<LogEntry> queue;

    @Override
    public void run() {
        IntStream.range(0, 10).forEach(i -> {
            try {
                LogEntry logEntry = new LogEntry(i, "Log Message: " + i);
                queue.put(logEntry);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        try {
            queue.put(new LogEntry(-1, "EOF"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
