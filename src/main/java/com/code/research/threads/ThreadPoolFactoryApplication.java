package com.code.research.threads;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadPoolFactoryApplication {

    public static void main(String[] args) {
        ExecutorService fixed = ThreadPoolFactory.newFixedThreadPool(4);
        ExecutorService cached = ThreadPoolFactory.newCachedThreadPool();
        ScheduledExecutorService sched = ThreadPoolFactory.newScheduledThreadPool(2);
        ThreadPoolExecutor custom = ThreadPoolFactory.newCustomThreadPool(
                2,      // core
                10,     // max
                30,     // keep-alive seconds
                100     // queue capacity
        );

        // submit tasks...
        fixed.submit(() -> doWork("fixed"));
        cached.submit(() -> doWork("cached"));
        sched.scheduleAtFixedRate(() -> doWork("sched"), 0, 1, TimeUnit.SECONDS);
        custom.submit(() -> doWork("custom"));

        // remember to shutdown when done!
    }

    private static void doWork(String tag) {
        log.info("[{}] executed by {}", tag, Thread.currentThread().getName());
    }

}
