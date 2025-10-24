package com.sive.bookingsystem.service.event.message;

import java.time.LocalDateTime;

public class Message {
    private String key;
    private String source;
    private String target;
    private LocalDateTime timestamp;
    private String id;
    private String description;
    private Data data;

    public Message(String source, String target, String key, LocalDateTime timestamp, String id, String description, Data data) {
        this.source = source;
        this.target = target;
        this.key = key;
        this.timestamp = timestamp;
        this.id = id;
        this.description = description;
        this.data = data;
    }

    public Message() {
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "key='" + key + '\'' +
                ", source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", timestamp=" + timestamp +
                ", id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", data=" + data +
                '}';
    }
}
