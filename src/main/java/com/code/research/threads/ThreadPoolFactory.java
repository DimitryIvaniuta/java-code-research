package com.code.research.threads;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Factory for professionally configured thread pools.
 */
public final class ThreadPoolFactory {

    private ThreadPoolFactory() {
    }

    /**
     * Creates a fixed-size thread pool.
     *
     * @param nThreads number of threads to keep in the pool
     */
    public static ExecutorService newFixedThreadPool(int nThreads) {
        ThreadFactory tf = new NamedThreadFactory("fixed-pool-%d", true);
        return Executors.newFixedThreadPool(nThreads, tf);
    }


    /**
     * Creates a cached thread pool that
     * - creates new threads as needed
     * - reuses idle threads after 60s
     */
    public static ExecutorService newCachedThreadPool() {
        ThreadFactory tf = new NamedThreadFactory("cached-pool-%d", true);
        return Executors.newCachedThreadPool(tf);
    }

    /**
     * Creates a scheduled thread pool for delayed and periodic tasks.
     *
     * @param corePoolSize number of threads to keep always alive
     */
    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        ThreadFactory tf = new NamedThreadFactory("sched-pool-%d", true);
        return Executors.newScheduledThreadPool(corePoolSize, tf);
    }

    /**
     * Creates a fully custom ThreadPoolExecutor.
     *
     * @param corePoolSize     the number of threads to keep in the pool, even if idle
     * @param maximumPoolSize  the maximum number of threads to allow
     * @param keepAliveSeconds time for excess threads to stay alive when idle
     * @param queueCapacity    capacity of the work queue
     */
    public static ThreadPoolExecutor newCustomThreadPool(
            int corePoolSize,
            int maximumPoolSize,
            long keepAliveSeconds,
            int queueCapacity
    ) {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(queueCapacity);
        ThreadFactory tf = new NamedThreadFactory("custom-pool-%d", false);
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveSeconds, TimeUnit.SECONDS,
                queue,
                tf,
                handler
        );
        // allow core threads to time out if you want even the core to shrink
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

    private static class NamedThreadFactory implements ThreadFactory {
        private final AtomicInteger counter = new AtomicInteger(1);
        private final String pattern;
        private final boolean daemon;

        /**
         * @param pattern thread name pattern, e.g. "my-pool-%d"
         * @param daemon  whether threads are daemon
         */
        NamedThreadFactory(String pattern, boolean daemon) {
            this.pattern = pattern;
            this.daemon = daemon;
        }

        @Override
        public Thread newThread(Runnable r) {
            String name = String.format(pattern, counter.getAndIncrement());
            Thread t = new Thread(r, name);
            t.setDaemon(daemon);
            // you can also set priority or UncaughtExceptionHandler here
            return t;
        }
    }
}
