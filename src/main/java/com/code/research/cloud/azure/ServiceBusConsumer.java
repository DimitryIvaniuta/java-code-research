package com.code.research.cloud.azure;

import com.azure.messaging.servicebus.*;

public class ServiceBusConsumer {

    public ServiceBusProcessorClient buildProcessor(String ns, String topic, String subscription) {
        return new ServiceBusClientBuilder()
                .fullyQualifiedNamespace(ns)
                .credential(new com.azure.identity.DefaultAzureCredentialBuilder().build())
                .processor()
                .topicName(topic)
                .subscriptionName(subscription)
                .processMessage(ctx -> {
                    String cid = ctx.getMessage().getCorrelationId();
                    String body = ctx.getMessage().getBody().toString();
                    // handle event (idempotent), then complete
                    ctx.complete();
                })
                .processError(err -> {
                    // log + metrics; message will be retried, then goes to DLQ by policy
                    System.err.println("SB error: " + err.getException().getMessage());
                })
                .buildProcessorClient();
    }
}
