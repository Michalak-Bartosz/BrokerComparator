package org.message.consumer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Comment {
    @Id
    private UUID uuid;
    private UUID reportUuid;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
}
