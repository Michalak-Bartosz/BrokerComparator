package org.message.producer.model;

import lombok.Data;

@Data
public class Comment {
    private Long id;
    private String description;

    public Comment(String description) {
        this.description = description;
    }
}
