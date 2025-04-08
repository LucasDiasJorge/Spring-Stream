package com.example.stream;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;

@Service
public class KafkaConsumerService {

    public void consumeAsync(String topic, BlockingQueue<String> messageQueue) {
        new Thread(() -> {
            KafkaConsumer<String, String> consumer = createConsumer();
            consumer.subscribe(Collections.singletonList(topic));

            while (!Thread.currentThread().isInterrupted()) {
                for (ConsumerRecord<String, String> record : consumer.poll(Duration.ofMillis(500))) {
                    messageQueue.offer(record.value());
                }
            }

            consumer.close();
        }).start();
    }

    private KafkaConsumer<String, String> createConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "stream-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return new KafkaConsumer<>(props);
    }
}
