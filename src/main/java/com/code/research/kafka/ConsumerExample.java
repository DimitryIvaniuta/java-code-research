package com.code.research.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class ConsumerExample {
    public static void main(String[] args) {
        var props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "orders-workers"); // single group
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false"); // manual commit
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // read existing data if first run

        try (var consumer = new KafkaConsumer<String, String>(props)) {
            consumer.subscribe(List.of("orders"));

            while (true) {
                ConsumerRecords<String, String> batch = consumer.poll(Duration.ofMillis(500));
                for (ConsumerRecord<String, String> rec : batch) {
                    System.out.printf("Consumed %s-%d @ offset %d key=%s value=%s%n",
                            rec.topic(), rec.partition(), rec.offset(), rec.key(), rec.value());
                }
                if (!batch.isEmpty()) consumer.commitSync(); // commit after processing
            }
        }
    }
}
