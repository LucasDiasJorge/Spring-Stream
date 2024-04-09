package com.example.stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;

@RestController
public class StreamController {

    @PostMapping(value = "/stream-data", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getStreamData(OutputStream outputStream, InputStream inputStream) throws IOException {
        // Simulação de dados de stream (substitua por sua lógica de geração de stream)
        for (int i = 0; i < 10; i++) {
            System.out.println("Running");
            outputStream.write(("Dado " + i + "\n").getBytes());
            System.out.println(inputStream.read()); // Reading from the input stream
            if(inputStream.read() == -1){
                System.out.println("Sai");
                break;
            }
            outputStream.flush();
            try {
                Thread.sleep(1000); // Simulando uma pausa de 1 segundo entre os dados
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}