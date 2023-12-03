package org.message.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Comment {
    private UUID uuid;
    private UUID reportUuid;
    private String description;
}
