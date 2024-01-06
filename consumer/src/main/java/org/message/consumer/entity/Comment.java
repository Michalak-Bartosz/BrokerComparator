package org.message.consumer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.message.consumer.entity.id.CommentIdKey;
import org.message.model.util.BrokerType;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@IdClass(CommentIdKey.class)
public class Comment {
    @Id
    private UUID uuid;
    @Id
    private UUID reportUuid;
    @Id
    @Enumerated(EnumType.STRING)
    private BrokerType brokerType;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
}
