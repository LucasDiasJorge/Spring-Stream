package com.example.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@RestController
public class StreamController {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaConsumerService kafkaConsumerService;

    public StreamController(KafkaConsumerService kafkaConsumerService) {
        this.kafkaConsumerService = kafkaConsumerService;
    }

    @GetMapping("/stream/{topic}")
    public void stream(@PathVariable String topic, HttpServletResponse response) throws IOException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");

        OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8);

        BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

        kafkaConsumerService.consumeAsync(topic, messageQueue);

        try {
            while (true) {
                String message = messageQueue.poll(Duration.ofSeconds(1).toMillis(), java.util.concurrent.TimeUnit.MILLISECONDS);
                if (message != null) {
                    StreamMessage msg = new StreamMessage(message);
                    writer.write(objectMapper.writeValueAsString(msg)+"\n");
                    writer.flush();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            writer.write(objectMapper.writeValueAsString(new StreamMessage("Stream interrompida")));
        } finally {
            writer.close();
        }
    }
}
