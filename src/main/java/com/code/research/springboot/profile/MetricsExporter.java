package com.code.research.springboot.profile;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MetricsExporter {

    private final ConcurrentHashMap<String, AtomicLong> counters = new ConcurrentHashMap<>();

    public void incrementCounter(String name) {
        counters.computeIfAbsent(name, k -> new AtomicLong()).incrementAndGet();
    }

    public void recordLatency(String name, Duration latency) {
        // in real app: push to Prometheus / Micrometer / etc.
        System.out.println("Latency metric [" + name + "] = " + latency.toMillis() + " ms");
    }

    public long getCounter(String name) {
        return counters.getOrDefault(name, new AtomicLong(0)).get();
    }
}
