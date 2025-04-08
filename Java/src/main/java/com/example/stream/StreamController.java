package com.example.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

@RestController
public class StreamController {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/stream")
    public void stream(HttpServletResponse response) throws IOException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");

        OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8);

        for (int i = 1; i <= 10; i++) {
            StreamMessage msg = new StreamMessage(i, "Mensagem #" + i);
            String json = objectMapper.writeValueAsString(msg);

            writer.write(json + "\n");
            writer.flush();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        writer.write(objectMapper.writeValueAsString(new StreamMessage(-1, "Fim da stream")) + "\n");
        writer.flush();
        writer.close();
    }


}
