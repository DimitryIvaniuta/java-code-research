package com.code.research.stream;

import java.util.concurrent.*;

public class BoundedBufferProcessor<T> {

    private final BlockingQueue<T> queue;
    private final ExecutorService executor;

    public BoundedBufferProcessor(int capacity) {
        this.queue    = new ArrayBlockingQueue<>(capacity);
        this.executor = Executors.newSingleThreadExecutor();
        startConsumer();
    }

    /** Producer calls this to enqueue items (blocks when full) */
    public void submit(T item) throws InterruptedException {
        queue.put(item);
    }

    private void startConsumer() {
        executor.submit(() -> {
            try {
                while (true) {
                    T item = queue.take();    // waits if queue empty
                    process(item);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    private void process(T item) {
        // expensive or blocking work here
    }

    public void shutdown() {
        executor.shutdownNow();
    }
}
