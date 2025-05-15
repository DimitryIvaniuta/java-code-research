package com.code.research.threads;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

/**
 * Utility to calculate recommended thread-pool sizes.
 * <p>
 * Uses the formula N_threads = N_cores / (1 − B), where
 * B = blockingCoefficient = waitTime / (computeTime + waitTime).
 */
public final class ThreadPoolSizeCalculator {

    /** Returns the number of available CPU cores on this machine. */
    public static int availableCores() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Calculates optimal thread count for mixed workloads.
     *
     * @param blockingCoefficient the fraction of time threads are blocked (0 ≤ B < 1)
     * @return recommended number of threads (at least 1)
     * @throws IllegalArgumentException if blockingCoefficient is not in [0,1)
     */
    public static int optimalThreadCount(double blockingCoefficient) {
        if (blockingCoefficient < 0 || blockingCoefficient >= 1) {
            throw new IllegalArgumentException("blockingCoefficient must be in [0,1)");
        }
        int cores = availableCores();
        int n = (int) Math.ceil(cores / (1.0 - blockingCoefficient));
        return Math.max(n, 1);
    }

    /**
     * Shortcut for CPU-bound tasks (no blocking expected).
     * Recommends cores + 1 to allow one extra thread for occasional GC or minor wait.
     */
    public static int cpuBoundThreadCount() {
        return availableCores() + 1;
    }

    /**
     * Shortcut for I/O-bound tasks.
     *
     * @param ioWaitFraction fraction of time spent waiting on I/O (0 ≤ W < 1)
     * @return recommended thread count = cores * (1 + W)
     */
    public static int ioBoundThreadCount(double ioWaitFraction) {
        if (ioWaitFraction < 0 || ioWaitFraction >= 1) {
            throw new IllegalArgumentException("ioWaitFraction must be in [0,1)");
        }
        int cores = availableCores();
        int n = (int) Math.ceil(cores * (1.0 + ioWaitFraction));
        return Math.max(n, 1);
    }

    /**
     * Adaptive calculation using current system CPU load.
     * If the CPU is already under heavy load, reduces thread count proportionally.
     *
     * @param blockingCoefficient workload blocking coefficient (0 ≤ B < 1)
     * @return adjusted thread count based on system load
     */
    public static int adaptiveThreadCount(double blockingCoefficient) {
        int base = optimalThreadCount(blockingCoefficient);
        double load = getProcessCpuLoad(); // 0.0 – 1.0
        // if load = 0.8, scale down to 20% headroom
        int adjusted = (int)Math.ceil(base * (1.0 - load));
        return Math.max(adjusted, 1);
    }

    /**
     * Returns the “recent CPU usage” of this JVM process (0.0 to 1.0).
     * Requires the com.sun.management.OperatingSystemMXBean.
     */
    private static double getProcessCpuLoad() {
        var bean = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        double load = bean.getProcessCpuLoad();
        return (load < 0) ? 0.0 : Math.min(load, 1.0);
    }

    // Prevent instantiation
    private ThreadPoolSizeCalculator() {}
}
