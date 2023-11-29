package org.message.consumer.kafka.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Comment {
    private UUID uuid;
    private UUID reportUuid;
    private String description;
}
