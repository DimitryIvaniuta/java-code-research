package com.code.research.kafka;

// Producer.java
import org.apache.kafka.clients.producer.*;
import java.util.Properties;

public class ProducerExample {

    public static void main(String[] args) throws Exception {
        var props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.ACKS_CONFIG, "all");                // safe
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true"); // avoid duplicates on retry

        try (var producer = new KafkaProducer<String, String>(props)) {
            String topic = "orders";
            String key   = "user-42";             // hash(key)%partitions → fixed partition
            String value = "{\"orderId\":1}";

            var record = new ProducerRecord<>(topic, key, value);
            RecordMetadata md = producer.send(record).get();  // sync send for demo
            System.out.printf("Produced to %s-%d @ offset %d%n", md.topic(), md.partition(), md.offset());
        }
    }
}
