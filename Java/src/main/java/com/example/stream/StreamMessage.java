package com.example.stream;

import java.util.UUID;

public class StreamMessage {
    public UUID id;
    public String mensagem;

    public StreamMessage(String mensagem) {
        this.id = UUID.randomUUID();
        this.mensagem = mensagem;
    }

    public StreamMessage() {}
}