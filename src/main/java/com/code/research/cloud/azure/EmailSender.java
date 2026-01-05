package com.code.research.cloud.azure;

import com.azure.communication.email.*;
import com.azure.communication.email.models.*;
import com.azure.core.credential.AzureKeyCredential;

public class EmailSender {

    private final EmailClient client;
    private final String from; // e.g. "no-reply@yourdomain.com"

    public EmailSender(String endpoint, String accessKey, String from) {
        this.client = new EmailClientBuilder()
                .endpoint(endpoint)
                .credential(new AzureKeyCredential(accessKey))
                .buildClient();
        this.from = from;
    }

    public void send(String to, String subject, String text) {
        EmailMessage message = new EmailMessage()
                .setSenderAddress(from)
                .setToRecipients(new EmailAddress(to))
                .setSubject(subject)
                .setBodyPlainText(text);

        client.beginSend(message, null);
    }
}
