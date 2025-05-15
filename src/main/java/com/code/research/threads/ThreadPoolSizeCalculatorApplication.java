package com.code.research.threads;

public class ThreadPoolSizeCalculatorApplication {

    public static void main(String[] args) {
        // 1. CPU-bound work
        int cpuThreads = ThreadPoolSizeCalculator.cpuBoundThreadCount();
        // e.g. cores=8 → 9 threads

        // 2. I/O-bound work (e.g. 50% waiting on I/O)
        int ioThreads = ThreadPoolSizeCalculator.ioBoundThreadCount(0.5);
        // e.g. cores=8 → 8 * (1 + 0.5) = 12 threads

        // 3. Mixed workload (e.g. 20% blocked)
        int mixedThreads = ThreadPoolSizeCalculator.optimalThreadCount(0.2);
        // e.g. cores=8 → 8 / (1−0.2) = 10 threads

        // 4. Adaptive sizing based on current CPU load
        int adaptive = ThreadPoolSizeCalculator.adaptiveThreadCount(0.3);
        // if CPU load=0.5, base=8/(1−0.3)=12 → adjusted=12*(1−0.5)=6
    }

}
