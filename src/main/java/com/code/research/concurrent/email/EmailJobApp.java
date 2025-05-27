package com.code.research.concurrent.email;

import java.util.concurrent.Executors;

public class EmailJobApp {

    public static void main(String[] args) {

        // Keep a pool of delivery workers (threads)
        try (AutoCloseableExecutorService executorService =
                     new AutoCloseableExecutorService(Executors.newFixedThreadPool(5))) {

            // Hand off packages to any available worker
            executorService.get().submit(new EmailJob("alice@example.com", "Welcome!", "Hi Alice…"));
            executorService.get().submit(new EmailJob("bob@example.com", "Reminder", "Hi Bob…"));
        }
    }
}
