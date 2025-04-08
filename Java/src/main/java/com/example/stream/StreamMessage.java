package com.example.stream;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class StreamMessage {
    public UUID id;
    public String from;
    public Date deliveredAt;
    public Map<String,Object> data;

    public StreamMessage() {}
}