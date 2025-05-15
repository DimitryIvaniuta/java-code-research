package com.code.research.threads;

public class DaemonThreadsApp {
    public static void main(String[] args) {
        Thread userThread = new Thread(() -> {
            try {
                Thread.sleep(2_000);
                System.out.println("User thread done");
            } catch (InterruptedException ignored) {}
        }, "user");

        Thread daemonThread = new Thread(() -> {
            try {
                // Will never reach this print if marked daemon
                Thread.sleep(5_000);
                System.out.println("Daemon thread done");
            } catch (InterruptedException ignored) {}
        }, "daemon");

        daemonThread.setDaemon(true);

        userThread.start();
        daemonThread.start();

        try {
            userThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Main exits → JVM terminates daemon prematurely");
    }
}
