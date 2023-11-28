package org.message.producer.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Comment {
    private UUID uuid;
    private UUID reportUuid;
    private String description;

    public Comment(String description) {
        this.uuid = UUID.randomUUID();
        this.description = description;
    }
}
