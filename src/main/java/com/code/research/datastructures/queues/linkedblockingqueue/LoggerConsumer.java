package com.code.research.datastructures.queues.linkedblockingqueue;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;

@Slf4j
@AllArgsConstructor
public class LoggerConsumer implements Runnable {

    private final BlockingQueue<LogEntry> queue;

    @Override
    public void run() {
        try{
            while (true) {
                LogEntry logEntry = queue.take();
                if (logEntry.getId() == -1) {
                    break;
                }
                log.info("Consuming {}", log);
            }
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

}
