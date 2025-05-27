package com.code.research.concurrent.email;

public class EmailJob implements Runnable {

    private final String recipient;

    private final String subject;

    private final String body;

    public EmailJob(final String recipient, final String subject, final String body) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;

    }

    @Override
    public void run() {
        EmailService.send(recipient, subject, body);
    }
}
