package com.code.research.cloud.azure;

import com.azure.messaging.servicebus.*;
import com.azure.identity.DefaultAzureCredentialBuilder;

public class ServiceBusPublisher {

    private final ServiceBusSenderClient sender;

    public ServiceBusPublisher(String fullyQualifiedNamespace, String topicName) {
        this.sender = new ServiceBusClientBuilder()
                .fullyQualifiedNamespace(fullyQualifiedNamespace) // e.g. mybus.servicebus.windows.net
                .credential(new DefaultAzureCredentialBuilder().build()) // Managed Identity
                .sender()
                .topicName(topicName)
                .buildClient();
    }

    public void publishOrderCreated(String correlationId, String payloadJson) {
        ServiceBusMessage msg = new ServiceBusMessage(payloadJson);
        msg.setCorrelationId(correlationId);
        msg.setSubject("ORDER_CREATED");
        msg.getApplicationProperties().put("eventType", "ORDER_CREATED");
        sender.sendMessage(msg);
    }
}
