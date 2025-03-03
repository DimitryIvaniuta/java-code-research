package com.code.research.collections;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class LockFreeReadExample {

    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("Apple", 1);
        map.put("Banana", 2);
        map.put("Cherry", 3);

        // Multiple threads can call get() concurrently without locking.
        Runnable readTask = () -> {
            for (String key : new String[]{"Apple", "Banana", "Cherry"}) {
                // get() is lock-free: it reads from the volatile table without acquiring a lock.
                Integer value = map.get(key);
                log.info("Thread Name: {} - {}:{}", Thread.currentThread().getName(), key, value);
            }
        };

        Thread t1 = new Thread(readTask, "Thread-1");
        Thread t2 = new Thread(readTask, "Thread-2");
        t1.start();
        t2.start();
    }

}
