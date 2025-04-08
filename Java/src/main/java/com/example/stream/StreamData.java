package com.example.stream;

public class StreamData {
    private long id;
    private String message;
    private long timestamp;

    public StreamData(long id, String message, long timestamp) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
    }

    public long getId() { return id; }
    public String getMessage() { return message; }
    public long getTimestamp() { return timestamp; }
}