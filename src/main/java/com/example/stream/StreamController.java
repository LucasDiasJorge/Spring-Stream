package com.example.stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;

@RestController
public class StreamController {

    @GetMapping(value = "/stream-data", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getStreamData(OutputStream outputStream) throws IOException {
        // Simulação de dados de stream (substitua por sua lógica de geração de stream)
        for (int i = 0; i < 100; i++) {
            System.out.println("Running");
            outputStream.write(("Dado " + i + "\n").getBytes());
            outputStream.flush();
            try {
                Thread.sleep(1000); // Simulando uma pausa de 1 segundo entre os dados
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
